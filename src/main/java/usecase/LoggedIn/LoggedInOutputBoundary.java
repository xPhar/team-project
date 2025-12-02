package usecase.LoggedIn;

/**
 * The output boundary for the Logged In Use Case.
 */
public interface LoggedInOutputBoundary {
    /**
     * Switches to the login view after logout.
     * @param response data required to switch to login view
     */
    void switchToLoginView(LoggedInOutputData response);

    /**
     * Switches to submit view after assignment selected.
     * @param response data required to switch to submit view
     */
    void switchToSubmitView(LoggedInOutputData response);

    /**
     * Switches to resubmit view after assignment selected.
     * @param response data required to switch to resubmit view
     */
    void switchToResubmitView(LoggedInOutputData response);

    /**
     * Switches to submission list view after assignment selected.
     * @param response data required to switch to submissionList view
     */
    void switchToSubmissionListView(LoggedInOutputData response);

    /**
     * Switches to class average view on button press.
     * @param response data required to switch to class average view
     */
    void switchToClassAverageView(LoggedInOutputData response);

    /**
     * Switches to create assignment view on button press.
     */
    void switchToCreateAssignmentView();

    /**
     * Prepares the failure view for the Login Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
