package interface_adapter.submission;

import interface_adapter.ViewModel;

/**
 * View model for submission view.
 */
public class SubmissionViewModel extends ViewModel<SubmissionState> {

    public SubmissionViewModel() {
        super("submission");
        setState(new SubmissionState());
    }
}
