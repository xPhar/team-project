package usecase.Assignments;

public interface AssignmentsInputBoundary {
    void execute(AssignmentsInputData inputData);

    void switchToCreateAssignmentView();

    void switchToSubmitView();

    void switchToResubmitView();

    void switchToSubmissionListView(String assignmentName);

    void switchToLoginView();
}
