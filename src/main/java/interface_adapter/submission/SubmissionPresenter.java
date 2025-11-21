package interface_adapter.submission;

import entity.Submission;
import interface_adapter.ViewManagerModel;
import interface_adapter.submission_list.SubmissionListViewModel;
import usecase.Grade.GradeOutputBoundary;
import usecase.Submission.SubmissionOutputBoundary;

import java.time.format.DateTimeFormatter;

/**
 * Presenter for the submission view.
 */
public class SubmissionPresenter implements
        SubmissionOutputBoundary,
        GradeOutputBoundary
{
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
    public void backToSubmissionListView() {
        viewManagerModel.setState(submissionListViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareGradeSuccessView() {
        viewModel.firePropertyChange("success");
    }

    @Override
    public void prepareGradeFailureView(String msg) {
        viewModel.getState().setGradeFailureMessage(msg);
        viewModel.firePropertyChange("failure");
    }
}
