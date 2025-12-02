package usecase.SubmissionList;

public class SubmissionListOutputData {
    private final String assignmentName;

    private final String submitter;
    private final String status;
    private final String submissionDate;
    private final String grade;
    private final String feedback;
    private final String submissionName;
    private final String maxGrade;

    public SubmissionListOutputData(String assignmentName,
                                    String submitter,
                                    String status,
                                    String submissionDate,
                                    String grade,
                                    String feedback,
                                    String submissionName,
                                    String maxGrade
    ) {
        this.assignmentName = assignmentName;
        this.submitter = submitter;
        this.status = status;
        this.submissionDate = submissionDate;
        this.grade = grade;
        this.feedback = feedback;
        this.submissionName = submissionName;
        this.maxGrade = maxGrade;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public String getSubmitter() {
        return submitter;
    }

    public String getStatus() {
        return status;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public String getGrade() {
        return grade;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getSubmissionName() {
        return submissionName;
    }

    public String getMaxGrade() {
        return maxGrade;
    }
}
