package usecase.Assignments;

public interface AssignmentsOutputBoundary {
    void prepareSuccessView(AssignmentsOutputData outputData);

    void prepareFailureView(String errorMessage);

    void switchToCreateAssignmentView();

    void switchToSubmitView();

    void switchToResubmitView();

    void switchToSubmissionListView(AssignmentsOutputData outputData);

    void switchToLoginView(AssignmentsOutputData outputData);
}
