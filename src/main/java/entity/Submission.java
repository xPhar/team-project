package entity;

import java.time.LocalDateTime;

public class Submission {
    public static final Status ON_TIME = Status.ON_TIME;
    public static final Status LATE = Status.LATE;
    public static final Status GRADED = Status.GRADED;
    public static final Status UNDER_REVIEW = Status.UNDER_REVIEW;
    private final String submitter;
    private final LocalDateTime submissionTime;
    private final String submissionName;
    private final String submissionData;
    private final double grade;
    private final Status status;
    private final String feedback;

    public Submission(String submitter, LocalDateTime submissionTime, String submissionName,
                      String submissionData, double grade, Status status, String feedback) {
        this.submitter = submitter;
        this.submissionTime = submissionTime;
        this.submissionName = submissionName;
        this.submissionData = submissionData;
        this.grade = grade;
        this.status = status;
        this.feedback = feedback;
    }

    public static SubmissionBuilder getBuilder() {
        return new SubmissionBuilder();
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

    public enum Status {
        ON_TIME,
        LATE,
        GRADED,
        UNDER_REVIEW
    }

    public static class SubmissionBuilder {
        private String submitter;
        private LocalDateTime submissionTime;
        private String submissionName;
        private String submissionData;
        private double grade;
        private Status status;
        private String feedback;

        SubmissionBuilder() {
        }

        /**
         * Sets the submitter of the submission.
         *
         * @param newSubmitter the name or identifier of the person submitting the work
         * @return the current instance*/
        public SubmissionBuilder submitter(String newSubmitter) {
            submitter = newSubmitter;
            return this;
        }

        /**
         * Sets the submission time for the submission.
         *
         * @param newSubmissionTime the timestamp representing the time of submission
         * @return the current instance of {@code SubmissionBuilder} for method chaining
         */
        public SubmissionBuilder submissionTime(LocalDateTime newSubmissionTime) {
            this.submissionTime = newSubmissionTime;
            return this;
        }

        /**
         * Sets the name of the submission.
         *
         * @param newSubmissionName the name of the submission
         * @return the current instance of {@code SubmissionBuilder} for method chaining
         */
        public SubmissionBuilder submissionName(String newSubmissionName) {
            this.submissionName = newSubmissionName;
            return this;
        }

        /**
         * Sets the submission data for the submission.
         *
         * @param newSubmissionData the content or data associated with the submission
         * @return the current instance of {@code SubmissionBuilder} for method chaining
         */
        public SubmissionBuilder submissionData(String newSubmissionData) {
            this.submissionData = newSubmissionData;
            return this;
        }

        /**
         * Sets the grade for the submission.
         *
         * @param newGrade the grade assigned to the submission
         * @return the current instance of {@code SubmissionBuilder} for method chaining
         */
        public SubmissionBuilder grade(double newGrade) {
            this.grade = newGrade;
            return this;
        }

        /**
         * Sets the status of the submission.
         *
         * @param newStatus the status of the submission
         * @return the current instance of {@code SubmissionBuilder} for method chaining
         */
        public SubmissionBuilder status(Status newStatus) {
            this.status = newStatus;
            return this;
        }

        /**
         * Sets the feedback for the submission.
         *
         * @param newFeedback the feedback or comments associated with the submission
         * @return the current instance of {@code SubmissionBuilder} for method chaining
         */
        public SubmissionBuilder feedback(String newFeedback) {
            this.feedback = newFeedback;
            return this;
        }

        /**
         * Builds and returns a new instance of the Submission class using the properties.
         *
         * @return a new Submission instance
         */
        public Submission build() {
            return new Submission(this.submitter, this.submissionTime, this.submissionName,
                    this.submissionData, this.grade, this.status, this.feedback);
        }
    }
}
