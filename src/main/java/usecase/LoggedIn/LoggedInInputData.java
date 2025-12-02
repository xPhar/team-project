package usecase.LoggedIn;

/**
 * The Input Data for the Logged In Use Case.
 */
public class LoggedInInputData {
    private final boolean logout;
    private final String assignment;

    public LoggedInInputData(boolean logout, String assignment) {
        this.logout = logout;
        this.assignment = assignment;
    }

    boolean getLogout() {
        return logout;
    }

    String getAssignment() {
        return assignment;
    }
}
