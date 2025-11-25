package interface_adapter.CreateAssignment;

import usecase.CreateAssignment.CreateAssignmentInputBoundary;
import usecase.CreateAssignment.CreateAssignmentInputData;

import java.time.LocalDateTime;
import java.util.List;

public class CreateAssignmentController {
    private final CreateAssignmentInputBoundary interactor;

    public CreateAssignmentController(CreateAssignmentInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String name, String description, LocalDateTime dueDate,
            double gracePeriod, List<String> supportedFileTypes, String courseCode) {
        CreateAssignmentInputData inputData = new CreateAssignmentInputData(
                name, description, dueDate, gracePeriod, supportedFileTypes, courseCode);
        interactor.execute(inputData);
    }

    public void switchToAssignmentView() {
        interactor.switchToAssignmentView();
    }
}
