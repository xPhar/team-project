package interface_adapter.Assignments;

import interface_adapter.ViewManagerModel;
import interface_adapter.submission_list.SubmissionListState;
import interface_adapter.submission_list.SubmissionListViewModel;
import interface_adapter.submission_list.SubmissionTableModel;
import usecase.Assignments.AssignmentsOutputBoundary;
import usecase.Assignments.AssignmentsOutputData;

public class AssignmentsPresenter implements AssignmentsOutputBoundary {
    private final AssignmentsViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final SubmissionListViewModel submissionListViewModel;

    public AssignmentsPresenter(
            AssignmentsViewModel viewModel,
            ViewManagerModel viewManagerModel,
            SubmissionListViewModel submissionListViewModel
    ) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.submissionListViewModel = submissionListViewModel;
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

    @Override
    public void switchToSubmissionListView(AssignmentsOutputData outputData) {
        final SubmissionListState submissionListState = submissionListViewModel.getState();
        submissionListState.setTitle(outputData.getAssignmentName());
        submissionListState.setTableModel(new SubmissionTableModel(outputData.getSubmissions()));
        submissionListViewModel.firePropertyChange();

        viewManagerModel.setState(submissionListViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
