package usecase.signup;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface SignupInputBoundary {

    /**
     * Executes the signup use case.
     * @param signupInputData the input data
     */
    void execute(SignupInputData signupInputData);
}