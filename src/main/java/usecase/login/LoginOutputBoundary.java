package usecase.login;

/**
 * The output boundary for the Login Use Case.
 */
public interface LoginOutputBoundary {
    /**
     * Switches to the logged in view for students after successful login.
     */
    void switchToStudentLoggedInView(LoginOutputData response);

    /**
     * Switches to the logged in view for instructors after successful login.
     */
    void switchToInstructorLoggedInView();

    /**
     * Prepares the failure view for the Login Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
