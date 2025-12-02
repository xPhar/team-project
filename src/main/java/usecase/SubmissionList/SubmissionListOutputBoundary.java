package usecase.SubmissionList;

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
