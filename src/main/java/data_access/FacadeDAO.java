package data_access;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.*;
import usecase.assignments.AssignmentsDataAccessInterface;
import usecase.class_average.ClassAverageUserDataAccessInterface;
import usecase.create_assignment.CreateAssignmentDataAccessInterface;
import usecase.edit_assignment.EditAssignmentDataAccessInterface;
import usecase.grade.GradeDataAccessInterface;
import usecase.logged_in.LoggedInDataAccessInterface;
import usecase.login.LoginDataAccessInterface;
import usecase.resubmit.ResubmitUserDataAccessInterface;
import usecase.signup.SignupDataAccessInterface;
import usecase.submission.SubmissionDataAccessInterface;
import usecase.submission_list.SubmissionListDataAccessInterface;
import usecase.submit.SubmitUserDataAccessInterface;

public class FacadeDAO implements
        LoginDataAccessInterface,
        SignupDataAccessInterface,
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
    private static final String COURSE_CODE = "course-course-CSC207";
    private static final String COURSE_PASSWORD = "COURSE";
    private static final String COURSE_DATA = "courseData";
    private static final String ASSIGNMENTS = "assignments";
    private static final String SUBMISSIONS = "submissions";
    private static final String SUBMITTED_AT = "submittedAt";
    private static final String FILE_NAME = "fileName";
    private static final String FILE_DATA = "fileData";
    private static final String STATUS = "status";
    private static final String FEEDBACK = "feedback";
    private static final String GRADE = "grade";
    private static final String TYPE = "type";

    private final GradeAPIDataAccessObject gradeDA;
    private final SessionDataAccessObject sessionDA;

    public FacadeDAO() {
        this.gradeDA = new GradeAPIDataAccessObject();
        this.sessionDA = new SessionDataAccessObject();
    }

    private String getCourseUserName(Course course) {
        return course.getCourseName();
    }

    private String getCourseUserName() {
        return COURSE_CODE;
    }

    // Submit
    @Override
    public void submit(File studentFile)
            throws IOException {
        final User student = sessionDA.getUser();
        final Assignment assignment = sessionDA.getAssignment();
        final Course course = sessionDA.getCourse();

        final Submission.SubmissionBuilder builder = Submission.getBuilder();

        builder.submitter(student.getName())
                .submissionTime(LocalDateTime.now())
                .submissionName(studentFile.getName())
                .submissionData(FileToStringDataAccessObject.readFileToString(studentFile))
                .feedback("");
        if (assignment.getDueDate().isBefore(LocalDateTime.now())) {
            builder.status(Submission.Status.LATE);
        }
        else {
            builder.status(Submission.Status.ON_TIME);
        }

        final Submission submission = builder.build();

        final JSONObject submissionJSON = submissionToJSON(submission);

        final JSONObject courseObject = gradeDA.getUserInfo(getCourseUserName(course));
        final JSONObject courseData = courseObject.getJSONObject(COURSE_DATA);
        final JSONObject assignmentDictionary = courseData.getJSONObject(ASSIGNMENTS);
        final JSONObject assignmentObject = assignmentDictionary.getJSONObject(assignment.getName());
        final JSONObject submissionArray = assignmentObject.getJSONObject(SUBMISSIONS);

        submissionArray.put(submission.getSubmitter(), submissionJSON);

        gradeDA.modifyUserInfoEndpoint(getCourseUserName(course), COURSE_PASSWORD, courseObject);
    }

    @Override
    public Assignment getAssignment() {
        return sessionDA.getAssignment();
    }

    private JSONObject submissionToJSON(Submission submission) {
        final JSONObject jobj = new JSONObject();
        jobj.put(SUBMITTED_AT, submission.getSubmissionTime().toString());
        jobj.put(FILE_NAME, submission.getSubmissionName());
        jobj.put(FILE_DATA, submission.getSubmissionData());
        jobj.put(STATUS, submission.getStatus().toString());
        jobj.put(FEEDBACK, submission.getFeedback());
        jobj.put(GRADE, submission.getGrade());

        return jobj;
    }

    // Average
    public List<Submission> getSubmissionsFor(String assignmentName) {
        final ArrayList<Submission> submissions = new ArrayList<>();

        JSONObject courseObject = gradeDA.getUserInfo(getCourseUserName());
        courseObject = courseObject.getJSONObject(COURSE_DATA);
        final JSONObject assignmentDictionary = courseObject.getJSONObject(ASSIGNMENTS);
        final JSONObject assignmentObject = assignmentDictionary.getJSONObject(assignmentName);
        final JSONObject submissionArray = assignmentObject.getJSONObject(SUBMISSIONS);
        final Iterator<String> keys = submissionArray.keys();
        while (keys.hasNext()) {
            final Submission.SubmissionBuilder builder = Submission.getBuilder();
            final String submitter = keys.next();
            final JSONObject submissionObj = submissionArray.getJSONObject(submitter);
            builder.submitter(submitter)
                    .submissionTime(LocalDateTime.parse(submissionObj.getString(SUBMITTED_AT)))
                    .submissionName(submissionObj.getString(FILE_NAME))
                    .submissionData(submissionObj.getString(FILE_DATA))
                    .status(Submission.Status.valueOf(submissionObj.getString(STATUS).toUpperCase()))
                    .grade(submissionObj.getDouble(GRADE))
                    .feedback(submissionObj.getString(FEEDBACK));

            submissions.add(builder.build());
        }

        return submissions;
    }

    public List<String> getAllAssignmentNames() {
        JSONObject courseObject = gradeDA.getUserInfo(getCourseUserName());
        courseObject = courseObject.getJSONObject(COURSE_DATA);
        final JSONObject assignmentDictionary = courseObject.getJSONObject(ASSIGNMENTS);
        final Iterator<String> keyIt = assignmentDictionary.keys();
        final ArrayList<String> assignmentNames = new ArrayList<>();
        while (keyIt.hasNext()) {
            assignmentNames.add(keyIt.next());
        }

        return assignmentNames;
    }

    public double getMyScore(String assignmentName, String username) {
        JSONObject courseObject = gradeDA.getUserInfo(getCourseUserName());
        courseObject = courseObject.getJSONObject(COURSE_DATA);
        final JSONObject assignmentDictionary = courseObject.getJSONObject(ASSIGNMENTS);
        final JSONObject assignmentObject = assignmentDictionary.getJSONObject(assignmentName);
        final JSONObject submissionObject = assignmentObject.getJSONObject(SUBMISSIONS);
        if (submissionObject.has(username)) {
            final JSONObject submissionObj = submissionObject.getJSONObject(username);
            return submissionObj.getDouble(GRADE);
        }
        // If the student didn't have a submission, we'll assume they're given a 0
        // (we don't have a way to manually add grades for students, feels a bit outside our scope)
        else {
            return 0.0;
        }
    }

    // Login / Register
    @Override
    public boolean existsByName(String username) {
        return gradeDA.checkUserExists(username);
    }

    public void save(User user) {
        // Create user with the API
        gradeDA.createUser(user.getName(), user.getPassword());

        // Update user and course data to enroll them into the default course
        enrollUser(user, COURSE_CODE);
    }

    private void enrollUser(User user, String course) {
        user.getCourses().add(course);

        // Save user to DB
        final JSONObject info = userToJSON(user);
        gradeDA.modifyUserInfoEndpoint(user.getName(), user.getPassword(), info);

        // Update course list
        final JSONObject courseInfo = gradeDA.getUserInfo(course);
        final JSONObject courseData = courseInfo.getJSONObject(COURSE_DATA);
        if (user.getUserType() == User.INSTRUCTOR) {
            courseData.getJSONArray("instructors").put(user.getName());
        }
        else if (user.getUserType() == User.STUDENT) {
            courseData.getJSONArray("students").put(user.getName());
        }

        gradeDA.modifyUserInfoEndpoint(course, COURSE_PASSWORD, courseInfo);
    }

    private JSONObject userToJSON(User user) {
        final String userType;
        if (user.getUserType() == User.STUDENT) {
            userType = "student";
        }
        else {
            userType = "instructor";
        }

        final JSONObject info = new JSONObject();
        info.put(TYPE, userType);

        final JSONObject userData = new JSONObject();
        userData.put("firstName", user.getFirstName());
        userData.put("lastName", user.getLastName());
        userData.put("courses", user.getCourses());
        info.put("userData", userData);

        return info;
    }

    @Override
    public User getUser(String username) throws DataAccessException {
        // Get the whole object first to allow us to get their password
        JSONObject userObj = gradeDA.getUserObject(username);
        final String password = userObj.getString("password");
        final String userType;
        try {
            // Everything else we need is in the info object
            userObj = userObj.getJSONObject("info");
            userType = userObj.getString(TYPE).toUpperCase();
            // The rest of the info is in the userData object... in hindsight we don't really need this anymore... :|
            userObj = userObj.getJSONObject("userData");
        }
        catch (JSONException ex) {
            throw new DataAccessException("User data is mangled. Please try a different account.");
        }
        final String firstName = userObj.getString("firstName");
        final String lastName = userObj.getString("lastName");

        final JSONArray courseList = userObj.getJSONArray("courses");
        final ArrayList<String> courses = new ArrayList<>();
        for (int i = 0; i < courseList.length(); i++) {
            courses.add(courseList.getString(i));
        }

        return new User(username,
                password,
                firstName,
                lastName,
                User.UserType.valueOf(userType),
                courses
        );
    }

    @Override
    public Submission getSubmission(Assignment assignment) {
        final String user = getCurrentUsername();
        final List<Submission> submissions = getSubmissionList(assignment.getName());
        for (Submission submission : submissions) {
            if (submission.getSubmitter().equals(user)) {
                return submission;
            }
        }
        return null;
    }

    @Override
    public String getCurrentUsername() {
        return sessionDA.getUser().getName();
    }

    public void setActiveUser(User user) {
        sessionDA.setUser(user);

        // Since we're currently going under the assumption of a single course, make that course active as well
        sessionDA.setCourse(getCourse(COURSE_CODE));
    }

    public Course getCourse(String courseName) {
        final JSONObject userObj = gradeDA.getUserInfo(courseName);

        if (!userObj.getString(TYPE).equals("course")) {
            return null;
        }

        final Course.CourseBuilder courseBuilder = Course.getBuilder();
        final JSONObject courseData = userObj.getJSONObject(COURSE_DATA);
        courseData.getJSONArray("instructors").toList()
                .stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .forEach(courseBuilder::addInstructor);
        courseData.getJSONArray("students").toList()
                .stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .forEach(courseBuilder::addInstructor);

        courseBuilder.courseName(courseName)
                     .courseCode(courseName)
                     .latePenalty(courseData.getString("latePolicy"));
        final JSONObject assignments = courseData.getJSONObject(ASSIGNMENTS);
        for (String assignmentName : assignments.keySet()) {
            final JSONObject assignmentObject = assignments.getJSONObject(assignmentName);

            final Assignment assignment = Assignment.builder()
                    .name(assignmentName)
                    .description(assignmentObject.getString("description"))
                    .creationDate(LocalDateTime.parse(assignmentObject.getString("creationDate")))
                    .dueDate(LocalDateTime.parse(assignmentObject.getString("dueDate")))
                    .gracePeriod(Double.parseDouble(assignmentObject.getString("gracePeriod")))
                    .supportedFileTypes(assignmentObject.getJSONArray("supportedFileTypes")
                            .toList()
                            .stream()
                            .filter(String.class::isInstance)
                            .map(String.class::cast)
                            .toList())
                    .build();

            courseBuilder.addAssignment(assignment);
        }

        return courseBuilder.build();
    }

    // Mark Assignment
    @Override
    public List<Submission> getSubmissionList() {
        final String assignmentName = sessionDA.getAssignment().getName();
        return getSubmissionsFor(assignmentName);
    }
    @Override
    public List<Submission> getSubmissionList(Assignment assignment) {
        return getSubmissionsFor(assignment.getName());
    }

    public List<Submission> getSubmissionList(String assignmentName) {
        return getSubmissionsFor(assignmentName);
    }

    public Submission getSubmission(String assignmentName, String submitter) throws DataAccessException {
        final List<Submission> submissionList = getSubmissionList(assignmentName);
        for (Submission submission : submissionList) {
            if (submission.getSubmitter().equals(submitter)) {
                return submission;
            }
        }
        throw new DataAccessException("No submission found for " + submitter);
    }

    public Submission getSubmissionForSubmissionView(String submitter) {
        final String assignmentName = sessionDA.getAssignment().getName();
        return getSubmission(assignmentName, submitter);
    }

    public void saveFile(File saveFile, String submitter) throws DataAccessException {
        final String assignmentName = sessionDA.getAssignment().getName();
        final Submission submission = getSubmission(assignmentName, submitter);
        try {
            FileToStringDataAccessObject.saveFileFromString(submission.getSubmissionData(), saveFile);
        }
        catch (IOException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    public void grade(String submitter, double grade, String feedback) {
        final Course course = sessionDA.getCourse();
        final String assignment = sessionDA.getAssignment().getName();

        final JSONObject userInfoObject = gradeDA.getUserInfo(getCourseUserName(course));
        final JSONObject courseObject = userInfoObject.getJSONObject(COURSE_DATA);
        final JSONObject assignmentDictionary = courseObject.getJSONObject(ASSIGNMENTS);
        final JSONObject assignmentObject = assignmentDictionary.getJSONObject(assignment);
        final JSONObject submissionArray = assignmentObject.getJSONObject(SUBMISSIONS);
        final JSONObject submissionObj = submissionArray.getJSONObject(submitter);
        submissionObj.put(GRADE, grade);
        submissionObj.put(FEEDBACK, feedback);
        submissionObj.put(STATUS, Submission.Status.GRADED.toString());

        gradeDA.modifyUserInfoEndpoint(getCourseUserName(course), COURSE_PASSWORD, userInfoObject);
    }

    // Some method I think we need (Indy)

    public List<Assignment> getAssignments() {
        return sessionDA.getCourse().getAssignments();
    }

    @Override
    public void resetSession() {
        this.sessionDA.resetSession();
    }

    @Override
    public User.UserType getUserType() {
        return sessionDA.getUser().getUserType();
    }

    @Override
    public boolean userHasSubmitted(Assignment assignment) {
        final Submission submission = getSubmission(assignment);

        return submission != null;
    }

    @Override
    public void setActiveAssignment(Assignment assignment) {
        sessionDA.setAssignment(assignment);
    }

    public Assignment getAssignment(String assignmentName) {
        final Course course = sessionDA.getCourse();
        JSONObject courseObject = gradeDA.getUserInfo(getCourseUserName(course));
        courseObject = courseObject.getJSONObject(COURSE_DATA);
        final JSONObject assignmentDictionary = courseObject.getJSONObject(ASSIGNMENTS);
        final JSONObject assignmentObj = assignmentDictionary.getJSONObject(assignmentName);

        final var builder = Assignment.builder();
        return builder.name(assignmentName)
                .description(assignmentObj.getString("description"))
                .creationDate(LocalDateTime.parse(assignmentObj.getString("creationDate")))
                .dueDate(LocalDateTime.parse(assignmentObj.getString("dueDate")))
                .gracePeriod(assignmentObj.getDouble("gracePeriod"))
                .supportedFileTypes(assignmentObj.getJSONArray("supportedFileTypes")
                        .toList().stream()
                        .filter(String.class::isInstance)
                        .map(String.class::cast)
                        .toList()
                )
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
        final String courseName = getCourseUserName();

        final JSONObject courseObject = gradeDA.getUserInfo(courseName);
        final JSONObject courseData = courseObject.getJSONObject(COURSE_DATA);
        final JSONObject assignmentsObject = courseData.getJSONObject(ASSIGNMENTS);

        assignmentsObject.put(assignment.getName(), assignmentToJSON(assignment));

        gradeDA.modifyUserInfoEndpoint(courseName, COURSE_PASSWORD, courseObject);
        sessionDA.setCourse(getCourse(courseName));
    }

    @Override
    public void updateAssignment(String courseCode, String originalName, Assignment assignment) {
        // Get the creation date of the original assignment and add it to the new assignment object
        final LocalDateTime creationDate = getAssignment(originalName).getCreationDate();
        assignment.setCreationDate(creationDate);

        // If the course name has stayed the same, we can simply use the saveAssignment method and overwrite the old one
        if (originalName.equals(assignment.getName())) {
            saveAssignment(courseCode, assignment);
        }
        else {
            final String courseName = getCourseUserName();
            final JSONObject courseObject = gradeDA.getUserInfo(courseName);
            final JSONObject assignmentsObject = courseObject.getJSONObject(COURSE_DATA).getJSONObject(ASSIGNMENTS);

            // Remove old assignment
            assignmentsObject.put(originalName, (Object) null);

            // Add updated assignment under the new name
            assignmentsObject.put(assignment.getName(), assignmentToJSON(assignment));

            gradeDA.modifyUserInfoEndpoint(courseName, COURSE_PASSWORD, courseObject);
            sessionDA.setCourse(getCourse(courseName));
        }
    }

    private JSONObject assignmentToJSON(Assignment assignment) {
        final JSONObject assignmentObj = new JSONObject();
        assignmentObj.put("creationDate", assignment.getCreationDate().toString());
        assignmentObj.put("description",  assignment.getDescription());
        assignmentObj.put("dueDate", assignment.getDueDate().toString());
        assignmentObj.put("gracePeriod", Double.toString(assignment.getGracePeriod()));
        assignmentObj.put("supportedFileTypes", new JSONArray(assignment.getSupportedFileTypes()));
        assignmentObj.put(SUBMISSIONS, new JSONObject());
        return assignmentObj;
    }
}
