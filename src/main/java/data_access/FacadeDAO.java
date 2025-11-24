package data_access;

import entity.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FacadeDAO {
    private final FileToStringDataAccessObject fsDA;
    private final GradeAPIDataAccessObject gradeDA;
    private final SessionDataAccessObject sessionDA;

    // TODO course password?
    private final String COURSE_PASSWORD = "";

    public FacadeDAO() {
        this.fsDA = new FileToStringDataAccessObject();
        this.gradeDA = new GradeAPIDataAccessObject();
        this.sessionDA = new SessionDataAccessObject();
    }

    private String getCourseUserName(Course course) {
        // TODO course username
        return "course-" + course.getCourseCode();
    }
    private String getCourseUserName() {
        // TODO course username
        return "course-CSC207";
    }


    // Submit
    public void submit(File studentFile, User student, Course course, Assignment assignment)
            throws IOException
    {
        Submission.SubmissionBuilder builder = Submission.getBuilder();
        builder.submitter(student.getName())
                .submissionTime(LocalDateTime.now())
                .submissionName(studentFile.getName())
                .submissionData(fsDA.readFileToString(studentFile))
                .status(Submission.Status.ON_TIME)
                .feedback("");
        if (assignment.getDueDate().isBefore(LocalDateTime.now())) {
            builder.status(Submission.Status.LATE);
        }

        Submission submission = builder.build();

        JSONObject submissionJSON = submissionToJSON(submission);

        JSONObject courseObject = gradeDA.getUserInfo(getCourseUserName(course));
        JSONObject assignmentDictionary = courseObject.getJSONObject("assignments");
        JSONObject assignmentObject = assignmentDictionary.getJSONObject(assignment.getName());
        JSONObject submissionArray = assignmentObject.getJSONObject("submissions");

        submissionArray.put(submission.getSubmitter(), submissionJSON);

        gradeDA.modifyUserInfoEndpoint(getCourseUserName(course), COURSE_PASSWORD, courseObject);
    }

    private JSONObject submissionToJSON(Submission submission) {
        JSONObject jobj = new JSONObject();
        jobj.put("submittedAt", submission.getSubmissionTime().toString());
        jobj.put("fileName", submission.getSubmissionName());
        jobj.put("fileData", submission.getSubmissionData());
        jobj.put("status", submission.getStatus().toString());
        jobj.put("feedback", submission.getFeedback());

        return jobj;
    }

    // Average
    public List<Submission> getSubmissionsFor(String assignmentName) {
        ArrayList<Submission> submissions = new ArrayList<>();

        JSONObject courseObject = gradeDA.getUserInfo(getCourseUserName());
        JSONObject assignmentDictionary = courseObject.getJSONObject("assignments");
        JSONObject assignmentObject = assignmentDictionary.getJSONObject(assignmentName);
        JSONObject submissionArray = assignmentObject.getJSONObject("submissions");
        Iterator<String> keys = submissionArray.keys();
        while (keys.hasNext()) {
            Submission.SubmissionBuilder builder = Submission.getBuilder();
            String submitter = keys.next();
            JSONObject submissionObj = submissionArray.getJSONObject(submitter);
            builder.submitter(submitter)
                    .submissionTime(LocalDateTime.parse(submissionObj.getString("submittedAt")))
                    .submissionName(submissionObj.getString("fileName"))
                    .submissionData(submissionObj.getString("fileData"))
                    .status(Submission.Status.valueOf(submissionObj.getString("status")))
                    .grade(submissionObj.getDouble("grade"))
                    .feedback(submissionObj.getString("feedback"));

            submissions.add(builder.build());
        }

        return submissions;
    }

    public List<String> getAllAssignmentNames() {
        JSONObject courseObject = gradeDA.getUserInfo(getCourseUserName());
        JSONObject assignmentDictionary = courseObject.getJSONObject("assignments");
        Iterator<String> keyIt = assignmentDictionary.keys();
        ArrayList<String> assignmentNames = new ArrayList<>();
        while (keyIt.hasNext()) {
            assignmentNames.add(keyIt.next());
        }

        return assignmentNames;
    }

    double getMyScore(String assignmentName, String username) {
        JSONObject courseObject = gradeDA.getUserInfo(getCourseUserName());
        JSONObject assignmentDictionary = courseObject.getJSONObject("assignments");
        JSONObject assignmentObject = assignmentDictionary.getJSONObject(assignmentName);
        JSONObject submissionArray = assignmentObject.getJSONObject("submissions");
        JSONObject submissionObj = submissionArray.getJSONObject(username);
        return submissionObj.getDouble("grade");
    }

    // Login / Register
    public boolean existsByName(String username) {
        return gradeDA.checkUserExists(username);
    }

    public void save(User user) {
        gradeDA.createUser(user.getName(), user.getPassword());
    }

    public User get(String username) throws DataAccessException {
        JSONObject userObj = gradeDA.getUserInfo(username);
        String userType = userObj.getString("type").toUpperCase();
        JSONArray courseList = userObj.getJSONArray("courses");

        ArrayList<String> courses = new ArrayList<>();
        for (int i = 0; i < courseList.length(); i++) {
            courses.add(courseList.getString(i));
        }

        return new User(username, "",
                User.USER_TYPE.valueOf(userType),
                courses
        );
    }

    public void setCurrentUsername(String name) {
        // TODO what to do here?
    }

    public String getCurrentUsername() {
        return sessionDA.getUser().getName();
    }

    public User getUser(String username) {
        return get(username);
    }

    public void setActiveUser(User user) {
        sessionDA.setUser(user);
    }

    // Mark Assignment
    public List<Submission> getSubmissionList(String assignmentName) {
        return getSubmissionsFor(assignmentName);
    }

    public Submission getSubmission(String assignmentName, String submitter) throws DataAccessException {
        List<Submission> submissionList = getSubmissionList(assignmentName);
        for (Submission submission : submissionList) {
            if (submission.getSubmitter().equals(submitter)) {
                return submission;
            }
        }
        throw new DataAccessException("No submission found for " + submitter);
    }

    public void saveFile(File saveFile) {
        // TODO if we store submission in session then we can just save the file from there
    }

    public void grade(String assignment, String submitter, double grade, String feedback) {
        Course course = sessionDA.getCourse();

        JSONObject courseObject = gradeDA.getUserInfo(getCourseUserName(course));
        JSONObject assignmentDictionary = courseObject.getJSONObject("assignments");
        JSONObject assignmentObject = assignmentDictionary.getJSONObject(assignment);
        JSONObject submissionArray = assignmentObject.getJSONObject("submissions");
        JSONObject submissionObj = submissionArray.getJSONObject(submitter);
        submissionObj.put("grade", grade);
        submissionObj.put("feedback", feedback);
        submissionObj.put("status", Submission.Status.GRADED.toString());

        gradeDA.modifyUserInfoEndpoint(getCourseUserName(course), COURSE_PASSWORD, courseObject);
    }

    // Some method I think we need (Indy)

    public List<Assignment> getAssignments() {
        Course course = sessionDA.getCourse();
        List<Assignment> assignments = new ArrayList<>();

        JSONObject courseObject = gradeDA.getUserInfo(getCourseUserName(course));
        JSONObject assignmentDictionary = courseObject.getJSONObject("assignments");
        Iterator<String> keyIt = assignmentDictionary.keys();
        while (keyIt.hasNext()) {
            String assignmentName = keyIt.next();
            JSONObject assignmentObj = assignmentDictionary.getJSONObject(assignmentName);
            Assignment.AssignmentBuilder builder = Assignment.builder();
            builder.name(assignmentName)
                    .description(assignmentObj.getString("description"))
                    .creationDate(LocalDateTime.parse(assignmentObj.getString("creationDate")))
                    .dueDate(LocalDateTime.parse(assignmentObj.getString("dueDate")))
                    .gracePeriod(assignmentObj.getDouble("gracePeriod"));
                    // TODO add supported file types
            assignments.add(builder.build());
        }

        return assignments;
    }

    public Assignment getAssignment(String assignmentName) {
        Course course = sessionDA.getCourse();
        JSONObject courseObject = gradeDA.getUserInfo(getCourseUserName(course));
        JSONObject assignmentDictionary = courseObject.getJSONObject("assignments");
        JSONObject assignmentObj =  assignmentDictionary.getJSONObject(assignmentName);

        Assignment.AssignmentBuilder builder = Assignment.builder();
        return builder.name(assignmentName)
                .description(assignmentObj.getString("description"))
                .creationDate(LocalDateTime.parse(assignmentObj.getString("creationDate")))
                .dueDate(LocalDateTime.parse(assignmentObj.getString("dueDate")))
                .gracePeriod(assignmentObj.getDouble("gracePeriod"))
                // TODO add supported file types
                .build();
    }
}
