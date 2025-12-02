package interfaceadapter.submission_list;

import interfaceadapter.ViewModel;

/**
 * View model for the submission list view.
 */
public class SubmissionListViewModel extends ViewModel<SubmissionListState> {
    public SubmissionListViewModel() {
        super("submissionList");
        setState(new SubmissionListState());
    }
}
