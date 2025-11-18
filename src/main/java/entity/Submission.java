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
        if (getStatus() == Status.GRADED) {
            return grade;
        }
        return -1;
    }

    public Status getStatus() {
        return status;
    }
}
