package interfaceadapter.submission;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.submission_list.SubmissionListViewModel;
import interfaceadapter.submission_list.SubmissionTableModel;
import usecase.Grade.GradeOutputBoundary;
import usecase.Submission.SubmissionOutputBoundary;
import usecase.Submission.SubmissionOutputData;

/**
 * Presenter for the submission view.
 */
public class SubmissionPresenter implements
        SubmissionOutputBoundary,
        GradeOutputBoundary {
    private final SubmissionViewModel viewModel;
    private final SubmissionListViewModel submissionListViewModel;
    private final ViewManagerModel viewManagerModel;

    public SubmissionPresenter(
            SubmissionViewModel viewModel,
            ViewManagerModel viewManagerModel,
            SubmissionListViewModel submissionListViewModel
    ) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.submissionListViewModel = submissionListViewModel;
    }

    @Override
    public void prepareGradeSuccessView() {
        viewModel.firePropertyChange("gradeSuccess");
    }

    @Override
    public void prepareGradeFailureView(String msg) {
        viewModel.getState().setGradeFailureMessage(msg);
        viewModel.firePropertyChange("gradeFailure");
    }

    @Override
    public void prepareDownloadSuccessView(String msg) {
        viewModel.getState().setDownloadSuccessMessage(msg);
        viewModel.firePropertyChange("downloadSuccess");
    }

    @Override
    public void prepareDownloadFailureView(String msg) {
        viewModel.getState().setDownloadFailureMessage(msg);
        viewModel.firePropertyChange("downloadFailure");
    }

    @Override
    public void backToSubmissionListView(SubmissionOutputData data) {
        final SubmissionTableModel tableModel = new SubmissionTableModel(data.getSubmissions());
        submissionListViewModel.getState().setTableModel(tableModel);
        submissionListViewModel.firePropertyChange();
        viewManagerModel.setState(submissionListViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
