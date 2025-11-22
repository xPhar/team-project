package data_access;

import entity.*;
import org.json.JSONObject;
import usecase.login.LoginDataAccessInterface;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FacadeDAO implements LoginDataAccessInterface {
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
        SubmissionBuilder builder = new SubmissionBuilder();
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
            SubmissionBuilder builder = new SubmissionBuilder();
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

    // Login
    @Override
    public boolean existsByName(String username) {
        return gradeDA.checkUserExists(username);
    }

    @Override
    public User getUser(String username) {
        gradeDA.getUser(username);
    }

    @Override
    public void setActiveUser(User user) {
        sessionDA.setUser(user);
    }
}
