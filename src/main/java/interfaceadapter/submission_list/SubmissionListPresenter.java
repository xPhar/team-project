package interfaceadapter.submission_list;

import interfaceadapter.Assignments.AssignmentsViewModel;
import interfaceadapter.ViewManagerModel;
import interfaceadapter.submission.SubmissionState;
import interfaceadapter.submission.SubmissionViewModel;
import usecase.SubmissionList.SubmissionListOutputBoundary;
import usecase.SubmissionList.SubmissionListOutputData;

/**
 * Presenter for the submission list view.
 */
public class SubmissionListPresenter implements SubmissionListOutputBoundary {
    private final SubmissionListViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final SubmissionViewModel submissionViewModel;
    private final AssignmentsViewModel assignmentsViewModel;

    public SubmissionListPresenter(
            SubmissionListViewModel viewModel,
            ViewManagerModel viewManagerModel,
            SubmissionViewModel submissionViewModel,
            AssignmentsViewModel assignmentsViewModel
    ) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.submissionViewModel = submissionViewModel;
        this.assignmentsViewModel = assignmentsViewModel;
    }

    @Override
    public void prepareSubmissionView(SubmissionListOutputData outputData) {
        final SubmissionState state = new SubmissionState();
        state.setSubmitter(outputData.getSubmitter());
        state.setSubmissionDate(outputData.getSubmissionDate());
        state.setStatus(outputData.getStatus());
        state.setGrade(outputData.getGrade());
        state.setFeedback(outputData.getFeedback());
        state.setSubmissionName(outputData.getSubmissionName());
        state.setMaxGrade(outputData.getMaxGrade());
        state.setAssignmentName(outputData.getAssignmentName());

        submissionViewModel.setState(state);
        submissionViewModel.firePropertyChange();
        viewManagerModel.setState(submissionViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void goToAssignmentView() {
        viewManagerModel.setState(assignmentsViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
