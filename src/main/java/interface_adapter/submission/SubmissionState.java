package interface_adapter.submission;

import java.time.LocalDateTime;

/**
 * The state for submission view.
 */
public class SubmissionState {
    private String submitter;
    private String status;
    private String submissionDate;
    private String grade;
    private String feedback;

    private String gradeFailureMessage;

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
}
