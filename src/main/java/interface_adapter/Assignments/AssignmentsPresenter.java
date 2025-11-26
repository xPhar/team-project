package interface_adapter.Assignments;

import interface_adapter.ViewManagerModel;
import usecase.Assignments.AssignmentsOutputBoundary;
import usecase.Assignments.AssignmentsOutputData;

public class AssignmentsPresenter implements AssignmentsOutputBoundary {
    private final AssignmentsViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public AssignmentsPresenter(AssignmentsViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(AssignmentsOutputData outputData) {
        AssignmentsState state = new AssignmentsState();
        state.setAssignments(outputData.getAssignments());
        state.setCourseName(outputData.getCourseName());
        state.setInstructor(outputData.isInstructor());
        state.setErrorMessage("");

        viewModel.setState(state);
        viewModel.firePropertyChange();
    }

    @Override
    public void prepareFailureView(String errorMessage) {
        AssignmentsState state = new AssignmentsState();
        state.setErrorMessage(errorMessage);

        viewModel.setState(state);
        viewModel.firePropertyChange();
    }

    @Override
    public void switchToCreateAssignmentView() {
        viewManagerModel.setState("Create Assignment");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToSubmitView() {
        viewManagerModel.setState("Submit");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToResubmitView() {
        viewManagerModel.setState("Resubmit");
        viewManagerModel.firePropertyChange();
    }
}
