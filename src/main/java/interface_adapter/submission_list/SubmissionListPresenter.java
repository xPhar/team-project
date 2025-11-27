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
    public void prepareSubmissionView(Submission outputData) {
        SubmissionState state = new SubmissionState();
        state.setSubmitter(outputData.getSubmitter());
        state.setSubmissionDate(
                outputData.getSubmissionTime().format(
                        DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")
                )
        );
        state.setStatus(outputData.getStatus().toString());
        state.setGrade(String.format("%.1f", outputData.getGrade()));
        state.setFeedback(outputData.getFeedback());

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
