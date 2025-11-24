package entity;

import java.time.LocalDateTime;

public class Submission {
    private String assignment;
    private String submitter;
    private LocalDateTime submissionTime;
    private String submissionName;
    private String submissionData;
    private double grade;
    private Status status;
    private String feedback;

    public enum Status {
        ON_TIME,
        LATE,
        GRADED,
        UNDER_REVIEW
    }

    public Submission(String assignment, String submitter, LocalDateTime submissionTime,
                    String submissionName, String submissionData, double grade, Status status, String feedback) {
        this.assignment = assignment;
        this.submitter = submitter;
        this.submissionTime = submissionTime;
        this.submissionName = submissionName;
        this.submissionData = submissionData;
        this.grade = grade;
        this.status = status;
        this.feedback = feedback;
    }

    public String getAssignment() {
        return assignment;
    }

    public String getSubmitter() {
        return submitter;
    }

    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }

    public String getSubmissionName() {
        return submissionName;
    }

    public String getSubmissionData() {
        return submissionData;
    }

    public double getGrade() {
        return grade;
    }

    public Status getStatus() {
        return status;
    }

    public String getFeedback() {
        return feedback;
    }

    public static SubmissionBuilder getBuilder() {
        return new SubmissionBuilder();
    }

    public static class SubmissionBuilder {
        private String assignment;
        private String submitter;
        private LocalDateTime submissionTime;
        private String submissionName;
        private String submissionData;
        private double grade;
        private Status status;
        private String feedback;

        SubmissionBuilder() {}

        public SubmissionBuilder assignment(String assignment) {
            this.assignment = assignment;
            return this;
        }

        public SubmissionBuilder submitter(String submitter) {
            this.submitter = submitter;
            return this;
        }

        public SubmissionBuilder submissionTime(LocalDateTime submissionTime) {
            this.submissionTime = submissionTime;
            return this;
        }

        public SubmissionBuilder submissionName(String submissionName) {
            this.submissionName = submissionName;
            return this;
        }

        public SubmissionBuilder submissionData(String submissionData) {
            this.submissionData = submissionData;
            return this;
        }

        public SubmissionBuilder grade(double grade) {
            this.grade = grade;
            return this;
        }

        public SubmissionBuilder status(Status status) {
            this.status = status;
            return this;
        }

        public SubmissionBuilder feedback(String feedback) {
            this.feedback = feedback;
            return this;
        }

        public Submission build() {
            return new Submission(this.assignment, this.submitter, this.submissionTime,
                    this.submissionData, this.submissionName, this.grade, this.status, this.feedback);
        }
    }
}
