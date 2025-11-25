package interface_adapter.EditAssignment;

import interface_adapter.Assignments.AssignmentsViewModel;
import interface_adapter.ViewManagerModel;
import usecase.EditAssignment.EditAssignmentInputBoundary;
import usecase.EditAssignment.EditAssignmentInputData;

import java.time.LocalDateTime;
import java.util.List;

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

    public void execute(String name, String courseCode, String description,
            LocalDateTime dueDate, List<String> supportedFileTypes) {
        EditAssignmentInputData inputData = new EditAssignmentInputData(
                name,
                courseCode,
                description,
                dueDate,
                supportedFileTypes);
        interactor.execute(inputData);
    }

    public void switchToAssignmentView() {
        viewManagerModel.setState(assignmentsViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    public void prepareEdit(String courseCode, String name, String description,
            LocalDateTime dueDate, double gracePeriod, List<String> supportedFileTypes) {
        EditAssignmentState state = new EditAssignmentState(courseCode, name, description,
                dueDate, gracePeriod, supportedFileTypes);
        editAssignmentViewModel.setState(state);
        editAssignmentViewModel.firePropertyChange();

        viewManagerModel.setState(editAssignmentViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
