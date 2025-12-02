package interfaceadapter.signup;

import usecase.signup.SignupInputBoundary;
import usecase.signup.SignupInputData;

/**
 * The controller for the Signup Use Case.
 */
public class SignupController {

    private final SignupInputBoundary signupUseCaseInteractor;

    public SignupController(SignupInputBoundary signupUseCaseInteractor) {
        this.signupUseCaseInteractor = signupUseCaseInteractor;
    }

    /**
     * Executes the Signup Use Case.
     * @param username the username of the user registering
     * @param password the password of the user registering
     * @param userRole the role of the user (Student or Instructor)
     * @param fullName the full name of the user
     */
    public void execute(String username, String password, String userRole, String fullName) {
        final SignupInputData signupInputData = new SignupInputData(
                username, password, userRole, fullName);

        signupUseCaseInteractor.execute(signupInputData);
    }

    /**
     * Cancels signup and returns to the login view.
     */
    public void cancel() {
        signupUseCaseInteractor.switchToLoginView();
    }
}
