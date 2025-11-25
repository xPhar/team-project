package usecase.logged_in;

/**
 * The Input Data for the Logged In Use Case.
 */
public class LoggedInInputData {
    private final boolean logout;
    private boolean submitted;
    private final String assignment;
    private final String userType;

    public LoggedInInputData(boolean logout, boolean submitted, String assignment, String userType) {
        this.logout = logout;
        this.submitted = submitted;
        this.assignment = assignment;
        this.userType = userType;
    }

    boolean getLogout() {
        return logout;
    }

    boolean getSubmitted() {
        return submitted;
    }

    String getAssignment() {
        return assignment;
    }

    String getUserType() {
        return userType;
    }
}
