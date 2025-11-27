package usecase.logged_in;

/**
 * Input Boundary for actions which are related to being logged in.
 */
public interface LoggedInInputBoundary {

    /**
     * Executes the logged in use case.
     * @param loggedInInputData the input data
     */
    void execute(LoggedInInputData loggedInInputData);
}
