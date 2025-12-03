package interface_adapter.assignments;

import usecase.assignments.AssignmentsInputBoundary;
import usecase.assignments.AssignmentsInputData;

public class AssignmentsController {
    private final AssignmentsInputBoundary interactor;

    public AssignmentsController(AssignmentsInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the Assignment-related Use Cases.
     */
    public void execute() {
        final AssignmentsInputData inputData = new AssignmentsInputData();
        interactor.execute(inputData);
    }

    /**
     * Switches to the Create Assignment view.
     */
    public void switchToCreateAssignmentView() {
        interactor.switchToCreateAssignmentView();
    }

    /**
     * Switches to the Submit Assignment view.
     */
    public void switchToSubmitView() {
        interactor.switchToSubmitView();
    }

    /**
     * Switches to the Resubmit Assignment view.
     */
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
