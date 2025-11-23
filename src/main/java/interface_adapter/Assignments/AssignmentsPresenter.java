package interface_adapter.Assignments;

import usecase.Assignments.AssignmentsOutputBoundary;
import usecase.Assignments.AssignmentsOutputData;

public class AssignmentsPresenter implements AssignmentsOutputBoundary {
    private final AssignmentsViewModel viewModel;

    public AssignmentsPresenter(AssignmentsViewModel viewModel) {
        this.viewModel = viewModel;
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
}
