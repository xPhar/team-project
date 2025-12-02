package usecase.Submission;

public interface SubmissionOutputBoundary {
    /**
     * Go back to submission list view.
     * @param data Submission list data.
     */
    void backToSubmissionListView(SubmissionOutputData data);

    /**
     * Show download success view.
     * @param msg the success message.
     */
    void prepareDownloadSuccessView(String msg);

    /**
     * Show download failure view.
     * @param msg the failure message.
     */
    void prepareDownloadFailureView(String msg);
}
