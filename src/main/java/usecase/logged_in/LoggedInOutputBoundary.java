package usecase.logged_in;

/**
 * The output boundary for the Logged In Use Case.
 */
public interface LoggedInOutputBoundary {
    /**
     * Switches to the login view after logout.
     */
    void switchToLoginView();

    /**
     * Switches to submit view after assignment selected.
     */
    void switchToSubmitView();

    /**
     * Switches to resubmit view after assignment selected.
     */
    void switchToResubmitView();

    /**
     * Switches to submission list view after assignment selected.
     */
    void switchToSubmissionListView();

    /**
     * Prepares the failure view for the Login Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
