package usecase.SubmissionList;

public interface SubmissionListInputBoundary {
    void getSubmissionList(String assignmentName);
    void getSubmission(String assignmentName, String submitter);
    void backToAssignment();
}
