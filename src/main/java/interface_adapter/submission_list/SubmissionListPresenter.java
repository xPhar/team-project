package interface_adapter.submission_list;

import usecase.SubmissionList.SubmissionListOutputBoundary;
import usecase.SubmissionList.SubmissionListOutputData;

/**
 * Presenter for the submission list view.
 */
public class SubmissionListPresenter implements SubmissionListOutputBoundary {
    private final SubmissionListViewModel viewModel;

    public SubmissionListPresenter(SubmissionListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareListView(SubmissionListOutputData submissionListOutputData) {
        SubmissionListState state = new SubmissionListState();
        state.setTableModel(new SubmissionTableModel(submissionListOutputData.getSubmissions()));
        state.setTitle(submissionListOutputData.getAssignmentName());

        viewModel.setState(state);
        viewModel.firePropertyChange("title");
        viewModel.firePropertyChange("tableModel");
    }

    @Override
    public void showListView() {
        
    }
}
