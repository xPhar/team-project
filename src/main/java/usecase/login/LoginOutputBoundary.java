package usecase.login;

/**
 * The output boundary for the Login Use Case.
 */
public interface LoginOutputBoundary {
    /**
     * Switches to logged in view after successful login.
     */
    void switchToLoggedInView(LoginOutputData response);



    /**
     * Prepares the failure view for the Login Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
