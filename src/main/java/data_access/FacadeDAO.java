package data_access;

import entity.*;
import interface_adapter.submission_list.SubmissionTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import usecase.Assignments.AssignmentsDataAccessInterface;
import usecase.CreateAssignment.CreateAssignmentDataAccessInterface;
import usecase.EditAssignment.EditAssignmentDataAccessInterface;
import usecase.Grade.GradeDataAccessInterface;
import usecase.Resubmit.ResubmitUserDataAccessInterface;
import usecase.Submission.SubmissionDataAccessInterface;
import usecase.SubmissionList.SubmissionListDataAccessInterface;
import usecase.Submit.SubmitUserDataAccessInterface;
import usecase.class_average.ClassAverageUserDataAccessInterface;
import usecase.logged_in.LoggedInDataAccessInterface;
import usecase.login.LoginDataAccessInterface;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FacadeDAO implements
        LoginDataAccessInterface,
        LoggedInDataAccessInterface,
        SubmitUserDataAccessInterface,
        ResubmitUserDataAccessInterface,
        SubmissionDataAccessInterface,
        SubmissionListDataAccessInterface,
        GradeDataAccessInterface,
        ClassAverageUserDataAccessInterface,
        AssignmentsDataAccessInterface,
        CreateAssignmentDataAccessInterface,
        EditAssignmentDataAccessInterface {
    private final FileToStringDataAccessObject fsDA;
    private final GradeAPIDataAccessObject gradeDA;
    private final SessionDataAccessObject sessionDA;

    // TODO course password?
    private final String COURSE_PASSWORD = "COURSE";

    public FacadeDAO() {
        this.fsDA = new FileToStringDataAccessObject();
        this.gradeDA = new GradeAPIDataAccessObject();
        this.sessionDA = new SessionDataAccessObject();
    }

    private String getCourseUserName(Course course) {
        // TODO course username
        return course.getCourseName();
    }
    private String getCourseUserName() {
        // TODO course username
        return "course-course-CSC207";
    }

    // Submit
    @Override
    public void submit(File studentFile)
            throws IOException
    {
        User student = sessionDA.getUser();
        Assignment assignment = sessionDA.getAssignment();
        Course course = sessionDA.getCourse();

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
        JSONObject courseData = courseObject.getJSONObject("courseData");
        JSONObject assignmentDictionary = courseData.getJSONObject("assignments");
        JSONObject assignmentObject = assignmentDictionary.getJSONObject(assignment.getName());
        JSONObject submissionArray = assignmentObject.getJSONObject("submissions");

        submissionArray.put(submission.getSubmitter(), submissionJSON);

        gradeDA.modifyUserInfoEndpoint(getCourseUserName(course), COURSE_PASSWORD, courseObject);
    }

    @Override
    public Assignment getAssignment() {
        return sessionDA.getAssignment();
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
        courseObject = courseObject.getJSONObject("courseData");
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
                    .status(Submission.Status.valueOf(submissionObj.getString("status").toUpperCase()))
                    .grade(submissionObj.getDouble("grade"))
                    .feedback(submissionObj.getString("feedback"));

            submissions.add(builder.build());
        }

        return submissions;
    }

    public List<String> getAllAssignmentNames() {
        JSONObject courseObject = gradeDA.getUserInfo(getCourseUserName());
        courseObject = courseObject.getJSONObject("courseData");
        JSONObject assignmentDictionary = courseObject.getJSONObject("assignments");
        Iterator<String> keyIt = assignmentDictionary.keys();
        ArrayList<String> assignmentNames = new ArrayList<>();
        while (keyIt.hasNext()) {
            assignmentNames.add(keyIt.next());
        }

        return assignmentNames;
    }

    public double getMyScore(String assignmentName, String username) {
        JSONObject courseObject = gradeDA.getUserInfo(getCourseUserName());
        courseObject = courseObject.getJSONObject("courseData");
        JSONObject assignmentDictionary = courseObject.getJSONObject("assignments");
        JSONObject assignmentObject = assignmentDictionary.getJSONObject(assignmentName);
        JSONObject submissionArray = assignmentObject.getJSONObject("submissions");
        JSONObject submissionObj = submissionArray.getJSONObject(username);
        return submissionObj.getDouble("grade");
    }

    // Login / Register
    @Override
    public boolean existsByName(String username) {
        return gradeDA.checkUserExists(username);
    }

    public void save(User user) {
        gradeDA.createUser(user.getName(), user.getPassword());
        String userType;
        if (user.getUserType() == user.STUDENT) {
            userType = "student";
        }
        else {
            userType = "instructor";
        }
        JSONObject info = new JSONObject(
                new StringBuilder()
                        .append("{\n")
                            .append("\t\"type\": \"")
                            .append(userType)
                            .append("\", \n")
                            .append("\t\"userData\": {\n")
                                .append("\t\t\"firstName\": \"")
                                .append(user.getFirstName())
                                .append("\", \n")
                                .append("\t\t\"lastName\": \"")
                                .append(user.getLastName())
                                .append("\", \n")
                                .append("\t\t\"courses\": []\n")
                            .append("\t}\n")
                        .append("}")
                        .toString()
        );
        gradeDA.modifyUserInfoEndpoint(user.getName(), user.getPassword(), info);
    }

    @Override
    public User getUser(String username) throws DataAccessException {
        // Get the whole object first to allow us to get their password
        JSONObject userObj = gradeDA.getUserObject(username);
        String password = userObj.getString("password");
        // Everything else we need is in the info object
        userObj = userObj.getJSONObject("info");
        // TODO: We can make a simple helper function to handle converting this
        String userType = userObj.getString("type").toUpperCase();
        // The rest of the info is in the userData object... in hindsight we don't really need this anymore... :|
        userObj = userObj.getJSONObject("userData");
        String firstName = userObj.getString("firstName");
        String lastName = userObj.getString("lastName");

        JSONArray courseList = userObj.getJSONArray("courses");
        ArrayList<String> courses = new ArrayList<>();
        for (int i = 0; i < courseList.length(); i++) {
            courses.add(courseList.getString(i));
        }

        return new User(username,
                password,
                firstName,
                lastName,
                User.USER_TYPE.valueOf(userType),
                courses
        );
    }

    @Override
    public Submission getSubmission(Assignment assignment) {
        String user = getCurrentUsername();
        List<Submission> submissions = getSubmissionList(assignment.getName());
        for (Submission submission : submissions) {
            if (submission.getSubmitter().equals(user)) {
                return submission;
            }
        }
        return null;
    }

    public void setCurrentUsername(String name) {
        // TODO what to do here?
    }

    public String getCurrentUsername() {
        return sessionDA.getUser().getName();
    }

    public void setActiveUser(User user) {
        sessionDA.setUser(user);

        // TODO: Implement course selecting if we get time
        // Since we're currently going under the assumption of a single course, make that course active as well
        sessionDA.setCourse(getCourse(user.getCourses().get(0)));
    }

    public Course getCourse(String courseName) {
        JSONObject userObj = gradeDA.getUserInfo(courseName);

        if (!userObj.getString("type").equals("course")) {
            return null;
        }

        Course.CourseBuilder courseBuilder = Course.getBuilder();
        JSONObject courseData = userObj.getJSONObject("courseData");
        courseData.getJSONArray("instructors").toList()
                .stream()
                .filter(o -> o instanceof String)
                .map(o -> (String) o)
                .forEach(courseBuilder::addInstructor);
        courseData.getJSONArray("students").toList()
                .stream()
                .filter(o -> o instanceof String)
                .map(o -> (String) o)
                .forEach(courseBuilder::addInstructor);

        courseBuilder.courseName(courseName)
                     .courseCode(courseName)
                     .latePenalty(courseData.getString("latePolicy"));
        JSONObject assignments = courseData.getJSONObject("assignments");
        for (String assignmentName : assignments.keySet()) {
            JSONObject assignmentObject = assignments.getJSONObject(assignmentName);

            Assignment assignment = Assignment.builder()
                    .name(assignmentName)
                    .description(assignmentObject.getString("description"))
                    .creationDate(LocalDateTime.parse(assignmentObject.getString("creationDate")))
                    .dueDate(LocalDateTime.parse(assignmentObject.getString("dueDate")))
                    .gracePeriod(Double.parseDouble(assignmentObject.getString("gracePeriod")))
                    .supportedFileTypes(assignmentObject.getJSONArray("supportedFileTypes")
                            .toList()
                            .stream()
                            .filter(o -> o instanceof String)
                            .map(o -> (String) o)
                            .toList())
                    .build();

            courseBuilder.addAssignment(assignment);
        }

        return courseBuilder.build();
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
        JSONObject courseData = courseObject.getJSONObject("courseData");
        JSONObject assignmentDictionary = courseData.getJSONObject("assignments");
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
        courseObject = courseObject.getJSONObject("courseData");
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

    @Override
    public void resetSession() {
        this.sessionDA.resetSession();
    }

    @Override
    public User.USER_TYPE getUserType() {
        return sessionDA.getUser().getUserType();
    }

    @Override
    public SubmissionTableModel getSubmissionTableModel(Assignment assignment) {
        List<Submission> submissionList = getSubmissionList(assignment.getName());

        return new SubmissionTableModel(submissionList);
    }

    @Override
    public boolean userHasSubmitted(Assignment assignment) {
        Submission submission = getSubmission(assignment);

        return submission != null;
    }

    @Override
    public void setActiveAssignment(Assignment assignment) {
        sessionDA.setAssignment(assignment);
    }

    public Assignment getAssignment(String assignmentName) {
        Course course = sessionDA.getCourse();
        JSONObject courseObject = gradeDA.getUserInfo(getCourseUserName(course));
        courseObject = courseObject.getJSONObject("courseData");
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

    @Override
    public User getCurrentUser() {
        return this.sessionDA.getUser();
    }

    @Override
    public String getCourseCode() {
        return sessionDA.getCourse().getCourseCode();
    }

    @Override
    public void saveAssignment(String courseCode, Assignment assignment) {
        Course course = sessionDA.getCourse();

        JSONObject courseObject = gradeDA.getUserInfo(getCourseUserName(course));
        JSONObject courseData = courseObject.getJSONObject("courseData");
        JSONObject assignmentsObject = courseData.getJSONObject("assignments");

        assignmentsObject.put(assignment.getName(), assignmentToJSON(assignment));

        gradeDA.modifyUserInfoEndpoint(getCourseUserName(course), COURSE_PASSWORD, courseObject);
    }

    @Override
    public void updateAssignment(String courseCode, String assignmentName, Assignment assignment) {
        // Assuming that the assignment exists, this functions identically to saveAssignment,
        // just overwriting the old assignment instead of creating a new one.
        // TODO: assignmentName is useless when assignment object holds the assignment name...
        saveAssignment(courseCode, assignment);
    }

    private JSONObject assignmentToJSON(Assignment assignment) {
        JSONObject assignmentObj = new JSONObject();
        assignmentObj.put("creationDate", assignment.getCreationDate().toString());
        assignmentObj.put("description",  assignment.getDescription());
        assignmentObj.put("dueDate", assignment.getDueDate().toString());
        assignmentObj.put("gracePeriod", Double.toString(assignment.getGracePeriod()));
        assignmentObj.put("supportedFileTypes", new JSONArray(assignment.getSupportedFileTypes()));
        assignmentObj.put("submissions", new JSONObject());
        return assignmentObj;
    }
}
