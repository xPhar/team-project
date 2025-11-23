package interface_adapter.CreateAssignment;

import entity.Assignment;
import usecase.CreateAssignment.CreateAssignmentInputBoundary;
import usecase.CreateAssignment.CreateAssignmentInputData;

public class CreateAssignmentController {
    private final CreateAssignmentInputBoundary interactor;

    public CreateAssignmentController(CreateAssignmentInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(Assignment assignment, String courseCode) {
        CreateAssignmentInputData inputData = new CreateAssignmentInputData(assignment, courseCode);
        interactor.execute(inputData);
    }

    public void switchToAssignmentView() {
        interactor.switchToAssignmentView();
    }
}
