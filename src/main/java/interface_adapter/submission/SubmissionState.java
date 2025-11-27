package interface_adapter.submission;

import java.time.LocalDateTime;

/**
 * The state for submission view.
 */
public class SubmissionState {
    private String assignmentName;
    private String submitter;
    private String status;
    private String submissionDate;
    private String grade;
    private String feedback;
    private String submissionName;
    private String maxGrade;

    private String gradeFailureMessage;
    private String downloadSuccessMessage;
    private String downloadFailureMessage;

    public SubmissionState() {}

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getGradeFailureMessage() {
        return gradeFailureMessage;
    }

    public void setGradeFailureMessage(String gradeFailureMessage) {
        this.gradeFailureMessage = gradeFailureMessage;
    }

    public String getDownloadSuccessMessage() {
        return downloadSuccessMessage;
    }

    public void setDownloadSuccessMessage(String downloadSuccessMessage) {
        this.downloadSuccessMessage = downloadSuccessMessage;
    }

    public String getDownloadFailureMessage() {
        return downloadFailureMessage;
    }

    public void setDownloadFailureMessage(String downloadFailureMessage) {
        this.downloadFailureMessage = downloadFailureMessage;
    }

    public String getSubmissionName() {
        return submissionName;
    }

    public void setSubmissionName(String submissionName) {
        this.submissionName = submissionName;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(String maxGrade) {
        this.maxGrade = maxGrade;
    }
}
