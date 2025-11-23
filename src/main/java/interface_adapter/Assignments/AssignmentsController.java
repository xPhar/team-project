package interface_adapter.Assignments;

import usecase.Assignments.AssignmentsInputBoundary;
import usecase.Assignments.AssignmentsInputData;

public class AssignmentsController {
    private final AssignmentsInputBoundary interactor;

    public AssignmentsController(AssignmentsInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String courseCode) {
        AssignmentsInputData inputData = new AssignmentsInputData(courseCode);
        interactor.execute(inputData);
    }
}
