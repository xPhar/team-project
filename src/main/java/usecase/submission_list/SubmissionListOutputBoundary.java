package usecase.submission_list;

public interface SubmissionListOutputBoundary {
    /**
     * Show the submission view with the submission data.
     * @param data submission data
     */
    void prepareSubmissionView(SubmissionListOutputData data);

    /**
     * Go to assignment view.
     */
    void goToAssignmentView();
}
