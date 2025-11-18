package entity;

import java.time.LocalDateTime;
import java.util.List;

public class SubmissionBuilder {
    private final Submission submission;

    public SubmissionBuilder() {
        this.submission = new Submission();
    }

    public SubmissionBuilder assignment(String assignment) {
        this.submission.setAssignment(assignment);
        return this;
    }

    public SubmissionBuilder submitter(String submitter) {
        this.submission.setSubmitter(submitter);
        return this;
    }

    public SubmissionBuilder submissionTime(LocalDateTime submissionTime) {
        this.submission.setSubmissionTime(submissionTime);
        return this;
    }

    public SubmissionBuilder submissionData(String submissionData) {
        this.submission.setSubmissionData(submissionData);
        return this;
    }

    public SubmissionBuilder grade(double grade) {
        this.submission.setGrade(grade);
        return this;
    }

    public SubmissionBuilder status(Submission.Status status) {
        this.submission.setStatus(status);
        return this;
    }

    public Submission build() {
        return this.submission;
    }
}
