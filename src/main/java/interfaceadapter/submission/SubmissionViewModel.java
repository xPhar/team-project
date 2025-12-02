package interfaceadapter.submission;

import interfaceadapter.ViewModel;

/**
 * View model for submission view.
 */
public class SubmissionViewModel extends ViewModel<SubmissionState> {

    public SubmissionViewModel() {
        super("submission");
        setState(new SubmissionState());
    }
}
