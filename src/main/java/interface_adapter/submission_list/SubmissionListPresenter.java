package interface_adapter.submission_list;

import interface_adapter.ViewManagerModel;
import usecase.SubmissionList.SubmissionListOutputBoundary;
import usecase.SubmissionList.SubmissionListOutputData;

/**
 * Presenter for the submission list view.
 */
public class SubmissionListPresenter implements SubmissionListOutputBoundary {
    private final SubmissionListViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public SubmissionListPresenter(
            SubmissionListViewModel viewModel,
            ViewManagerModel viewManagerModel
    ) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareListView(SubmissionListOutputData submissionListOutputData) {
        SubmissionListState state = new SubmissionListState();
        state.setTableModel(new SubmissionTableModel(submissionListOutputData.getSubmissions()));
        state.setTitle(submissionListOutputData.getAssignmentName());

        viewModel.setState(state);
        viewModel.firePropertyChange("title");
        viewModel.firePropertyChange("tableModel");
        viewManagerModel.setState(viewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void showListView() {
        viewManagerModel.setState(viewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
