package interface_adapter.submission_list;

import interface_adapter.ViewModel;

/**
 * View model for the submission list view.
 */
public class SubmissionListViewModel extends ViewModel<SubmissionListState> {
    public SubmissionListViewModel() {
        super("submissionList");
        setState(new SubmissionListState());
    }
}
