package interface_adapter.submission_list;

import entity.Submission;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.submission.SubmissionState;
import interface_adapter.submission.SubmissionViewModel;
import usecase.SubmissionList.SubmissionListOutputBoundary;
import usecase.SubmissionList.SubmissionListOutputData;

import java.time.format.DateTimeFormatter;

/**
 * Presenter for the submission list view.
 */
public class SubmissionListPresenter implements SubmissionListOutputBoundary {
    private final SubmissionListViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final SubmissionViewModel submissionViewModel;
    private final LoggedInViewModel loggedInViewModel;

    public SubmissionListPresenter(
            SubmissionListViewModel viewModel,
            ViewManagerModel viewManagerModel,
            SubmissionViewModel submissionViewModel,
            LoggedInViewModel loggedInViewModel
    ) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.submissionViewModel = submissionViewModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void prepareSubmissionView(SubmissionListOutputData outputData) {
        SubmissionState state = new SubmissionState();
        state.setSubmitter(outputData.getSubmitter());
        state.setSubmissionDate(outputData.getSubmissionDate());
        state.setStatus(outputData.getStatus());
        state.setGrade(outputData.getGrade());
        state.setFeedback(outputData.getFeedback());
        state.setSubmissionName(outputData.getSubmissionName());

        submissionViewModel.setState(state);
        submissionViewModel.firePropertyChange();
        viewManagerModel.setState(submissionViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void goToAssignmentView() {
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
