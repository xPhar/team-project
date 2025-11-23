package entity;

import java.time.LocalDateTime;

public class Submission {
    private String assignment;
    private String submitter;
    private LocalDateTime submissionTime;
    private String submissionData;
    private double grade;
    private Status status;

    public enum Status {
        ON_TIME,
        LATE,
        GRADED,
        UNDER_REVIEW
    }

    public Submission(String assignment, String submitter, LocalDateTime submissionTime,
                    String submissionData, double grade, Status status) {
        this.assignment = assignment;
        this.submitter = submitter;
        this.submissionTime = submissionTime;
        this.submissionData = submissionData;
        this.grade = grade;
        this.status = status;
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

    public String getSubmissionData() {
        return submissionData;
    }

    public double getGrade() {
        return grade;
    }

    public Status getStatus() {
        return status;
    }

    public static SubmissionBuilder getBuilder() {
        return new SubmissionBuilder();
    }

    public static class SubmissionBuilder {
        private String assignment;
        private String submitter;
        private LocalDateTime submissionTime;
        private String submissionData;
        private double grade;
        private Status status;

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

        public Submission build() {
            return new Submission(this.assignment, this.submitter, this.submissionTime,
                    this.submissionData, this.grade, this.status);
        }
    }
}
