package interface_adapter.Assignments;

import usecase.Assignments.AssignmentsInputBoundary;
import usecase.Assignments.AssignmentsInputData;

public class AssignmentsController {
    private final AssignmentsInputBoundary interactor;

    public AssignmentsController(AssignmentsInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute() {
        AssignmentsInputData inputData = new AssignmentsInputData();
        interactor.execute(inputData);
    }

    public void switchToCreateAssignmentView() {
        interactor.switchToCreateAssignmentView();
    }

    public void switchToSubmitView() {
        interactor.switchToSubmitView();
    }

    public void switchToResubmitView() {
        interactor.switchToResubmitView();
    }

    public void switchToSubmissionListView(String assignmentName) {
        interactor.switchToSubmissionListView(assignmentName);
    }

    public void switchToLoginView() {
        interactor.switchToLoginView();
    }
}
