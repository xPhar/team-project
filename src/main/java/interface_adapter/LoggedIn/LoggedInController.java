package interface_adapter.LoggedIn;

import usecase.LoggedIn.LoggedInInputBoundary;
import usecase.LoggedIn.LoggedInInputData;

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

