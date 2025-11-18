package interface_adapter.signup;

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
     */
    public void execute(String username, String password) {
        final SignupInputData signupInputData = new SignupInputData(
                username, password);

        signupUseCaseInteractor.execute(signupInputData);
    }
}
