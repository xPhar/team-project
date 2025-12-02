package interface_adapter.CreateAssignment;

import java.time.LocalDateTime;
import java.util.List;

import usecase.CreateAssignment.CreateAssignmentInputBoundary;
import usecase.CreateAssignment.CreateAssignmentInputData;

public class CreateAssignmentController {
    private final CreateAssignmentInputBoundary interactor;

    public CreateAssignmentController(CreateAssignmentInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the creation of an assignment by passing the required input data to the use case interactor.
     *
     * @param name              the name of the assignment
     * @param description       the description of the assignment
     * @param dueDate           the due date and time for the assignment
     * @param gracePeriod       the permissible grace period (in hours) for late submissions
     * @param supportedFileTypes the list of supported file types for the assignment submissions
     * @param courseCode        the course code associated with this assignment
     */
    public void execute(String name, String description, LocalDateTime dueDate,
            double gracePeriod, List<String> supportedFileTypes, String courseCode) {
        final CreateAssignmentInputData inputData = new CreateAssignmentInputData(
                name, description, dueDate, gracePeriod, supportedFileTypes, courseCode);
        interactor.execute(inputData);
    }

    /**
     * Switches the application view to the assignment view.
     * This method invokes the corresponding functionality in the interactor layer,
     * ensuring that the user interface transitions to display the assignments section.
     */
    public void switchToAssignmentView() {
        interactor.switchToAssignmentView();
    }
}
