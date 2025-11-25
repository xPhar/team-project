package usecase.logged_in;

/**
 * The Input Data for the Logged In Use Case.
 */
public class LoggedInInputData {
    private final boolean logout;
    private final String assignment;
    private final String userType;

    public LoggedInInputData(boolean logout, String assignment, String userType) {
        this.logout = logout;
        this.assignment = assignment;
        this.userType = userType;
    }

    boolean  getLogout() {
        return logout;
    }
    String getAssignment() {
        return assignment;
    }

    String getUserType() {
        return userType;
    }
}
