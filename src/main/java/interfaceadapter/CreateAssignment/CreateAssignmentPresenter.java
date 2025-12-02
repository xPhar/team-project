package interfaceadapter.CreateAssignment;

import interfaceadapter.Assignments.AssignmentsViewModel;
import interfaceadapter.ViewManagerModel;
import usecase.CreateAssignment.CreateAssignmentOutputBoundary;
import usecase.CreateAssignment.CreateAssignmentOutputData;

public class CreateAssignmentPresenter implements CreateAssignmentOutputBoundary {
    private final CreateAssignmentViewModel createAssignmentViewModel;
    private final AssignmentsViewModel assignmentViewModel;
    private final ViewManagerModel viewManagerModel;

    public CreateAssignmentPresenter(CreateAssignmentViewModel createAssignmentViewModel,
                                     AssignmentsViewModel assignmentViewModel,
                                     ViewManagerModel viewManagerModel) {
        this.createAssignmentViewModel = createAssignmentViewModel;
        this.assignmentViewModel = assignmentViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(CreateAssignmentOutputData outputData) {
        final CreateAssignmentState state = createAssignmentViewModel.getState();
        state.setSuccess(true);
        state.setErrorMessage("");
        createAssignmentViewModel.setState(state);
        createAssignmentViewModel.firePropertyChange();

        viewManagerModel.setState(assignmentViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailureView(String errorMessage) {
        final CreateAssignmentState state = createAssignmentViewModel.getState();
        state.setSuccess(false);
        state.setErrorMessage(errorMessage);
        createAssignmentViewModel.setState(state);
        createAssignmentViewModel.firePropertyChange();
    }

    @Override
    public void switchToAssignmentView() {
        viewManagerModel.setState(assignmentViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
