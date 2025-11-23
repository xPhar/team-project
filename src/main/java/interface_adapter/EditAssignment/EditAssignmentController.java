package interface_adapter.EditAssignment;

import entity.Assignment;
import interface_adapter.Assignments.AssignmentsViewModel;
import interface_adapter.ViewManagerModel;
import usecase.EditAssignment.EditAssignmentInputBoundary;
import usecase.EditAssignment.EditAssignmentInputData;

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

    public void execute(String originalName, Assignment assignment, String courseCode) {
        EditAssignmentInputData inputData = new EditAssignmentInputData(
                originalName,
                assignment.getName(),
                assignment.getDescription(),
                assignment.getDueDate(),
                assignment.getGracePeriod(),
                assignment.getLatePenalty(),
                assignment.getSupportedFileTypes(),
                courseCode);
        interactor.execute(inputData);
    }

    public void switchToAssignmentView() {
        viewManagerModel.setState(assignmentsViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    public void prepareEdit(Assignment assignment) {
        EditAssignmentState state = new EditAssignmentState();
        state.setAssignment(assignment);
        state.setOriginalName(assignment.getName());
        editAssignmentViewModel.setState(state);
        editAssignmentViewModel.firePropertyChange();

        viewManagerModel.setState(editAssignmentViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
