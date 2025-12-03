package interface_adapter.logged_in;

import usecase.logged_in.LoggedInInputBoundary;
import usecase.logged_in.LoggedInInputData;

public class LoggedInController {
    private final LoggedInInputBoundary loggedInUseCaseInteractor;

    public LoggedInController(LoggedInInputBoundary loggedInUseCaseInteractor) {
        this.loggedInUseCaseInteractor = loggedInUseCaseInteractor;
    }

    /**
     * Executes the LoggedIn Use Case.
     * @param logout whether the user requested to log out
     * @param assignment the assignment selected by the user (or null if none selected)
     */
    public void execute(boolean logout, String assignment) {
        final LoggedInInputData loggedInInputData = new LoggedInInputData(logout, assignment);

        loggedInUseCaseInteractor.execute(loggedInInputData);
    }
}

