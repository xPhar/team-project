package interface_adapter.edit_assignment;

import java.time.LocalDateTime;
import java.util.List;

import interface_adapter.assignments.AssignmentsViewModel;
import interface_adapter.ViewManagerModel;
import usecase.edit_assignment.EditAssignmentInputBoundary;
import usecase.edit_assignment.EditAssignmentInputData;

public class EditAssignmentController {
    private final EditAssignmentInputBoundary interactor;
    private final ViewManagerModel viewManagerModel;
    private final AssignmentsViewModel assignmentsViewModel;
    private final EditAssignmentViewModel editAssignmentViewModel;

    public EditAssignmentController(EditAssignmentInputBoundary interactor,
            ViewManagerModel viewManagerModel,
            AssignmentsViewModel assignmentsViewModel,
            EditAssignmentViewModel editAssignmentViewModel) {
        this.interactor = interactor;
        this.viewManagerModel = viewManagerModel;
        this.assignmentsViewModel = assignmentsViewModel;
        this.editAssignmentViewModel = editAssignmentViewModel;
    }

    /**
     * Executes the editing of an assignment by creating and passing the necessary input data
     * to the interactor.
     *
     * @param name the name of the assignment
     * @param courseCode the course code associated with the assignment
     * @param description the description of the assignment
     * @param dueDate the due date and time for the assignment
     * @param supportedFileTypes the list of file types that can be submitted for the assignment
     */
    public void execute(String originalName, String updatedName, String courseCode, String description,
            LocalDateTime dueDate, List<String> supportedFileTypes) {

        final EditAssignmentInputData inputData = new EditAssignmentInputData(
                originalName,
                updatedName,
                courseCode,
                description,
                dueDate,
                supportedFileTypes);
        interactor.execute(inputData);
    }

    /**
     * Switches the current view to the assignments view.
     * This method updates the state of the {@code viewManagerModel} to the view name provided
     * by the {@code assignmentsViewModel}, and fires a property change event to notify observers
     * of the state change.
     */
    public void switchToAssignmentView() {
        viewManagerModel.setState(assignmentsViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    /**
     * Prepares the view and state for editing an assignment. The method updates the
     * {@code EditAssignmentViewModel} with provided assignment details and notifies observers
     * about the state change. It also switches the view to the edit assignment view.
     *
     * @param courseCode the course code associated with the assignment
     * @param name the name of the assignment
     * @param description the description of the assignment
     * @param dueDate the due date and time for the assignment
     * @param gracePeriod the grace period allowed for late submissions in days
     * @param supportedFileTypes the list of file types that can be submitted for the assignment
     */
    public void prepareEdit(String courseCode, String name, String description,
            LocalDateTime dueDate, double gracePeriod, List<String> supportedFileTypes) {

        final EditAssignmentState state = new EditAssignmentState(courseCode, name, description,
                dueDate, gracePeriod, supportedFileTypes);
        editAssignmentViewModel.setState(state);
        editAssignmentViewModel.firePropertyChange();

        viewManagerModel.setState(editAssignmentViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
