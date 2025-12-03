package interface_adapter.assignments;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.submission_list.SubmissionListState;
import interface_adapter.submission_list.SubmissionListViewModel;
import interface_adapter.submission_list.SubmissionTableModel;
import usecase.assignments.AssignmentsOutputBoundary;
import usecase.assignments.AssignmentsOutputData;

public class AssignmentsPresenter implements AssignmentsOutputBoundary {
    private final AssignmentsViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final SubmissionListViewModel submissionListViewModel;
    private final LoginViewModel loginViewModel;

    public AssignmentsPresenter(
            AssignmentsViewModel viewModel,
            ViewManagerModel viewManagerModel,
            SubmissionListViewModel submissionListViewModel,
            LoginViewModel loginViewModel
    ) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.submissionListViewModel = submissionListViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(AssignmentsOutputData outputData) {
        final AssignmentsState state = new AssignmentsState();
        state.setAssignments(outputData.getAssignments());
        state.setCourseName(outputData.getCourseName());
        state.setInstructor(outputData.isInstructor());
        state.setErrorMessage("");

        viewModel.setState(state);
        viewModel.firePropertyChange();
    }

    @Override
    public void prepareFailureView(String errorMessage) {
        final AssignmentsState state = new AssignmentsState();
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

    @Override
    public void switchToSubmissionListView(AssignmentsOutputData outputData) {
        final SubmissionListState submissionListState = submissionListViewModel.getState();
        submissionListState.setTitle(outputData.getAssignmentName());
        submissionListState.setTableModel(new SubmissionTableModel(outputData.getSubmissions()));
        submissionListViewModel.firePropertyChange();

        viewManagerModel.setState(submissionListViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToLoginView(AssignmentsOutputData outputData) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setUsername(outputData.getUsername());
        loginViewModel.firePropertyChange();

        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
