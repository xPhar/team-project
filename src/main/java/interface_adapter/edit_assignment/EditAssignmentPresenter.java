package interface_adapter.edit_assignment;

import interface_adapter.assignments.AssignmentsViewModel;
import interface_adapter.ViewManagerModel;
import usecase.edit_assignment.EditAssignmentOutputBoundary;

public class EditAssignmentPresenter implements EditAssignmentOutputBoundary {
    private final EditAssignmentViewModel editAssignmentViewModel;
    private final AssignmentsViewModel assignmentsViewModel;
    private final ViewManagerModel viewManagerModel;

    public EditAssignmentPresenter(EditAssignmentViewModel editAssignmentViewModel,
            AssignmentsViewModel assignmentsViewModel,
            ViewManagerModel viewManagerModel) {
        this.editAssignmentViewModel = editAssignmentViewModel;
        this.assignmentsViewModel = assignmentsViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView() {
        final EditAssignmentState state = editAssignmentViewModel.getState();
        state.setSuccess(true);
        state.setErrorMessage(null);
        editAssignmentViewModel.firePropertyChange();

        viewManagerModel.setState(assignmentsViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailureView(String error) {
        final EditAssignmentState state = editAssignmentViewModel.getState();
        state.setErrorMessage(error);
        state.setSuccess(false);
        editAssignmentViewModel.firePropertyChange();
    }
}
