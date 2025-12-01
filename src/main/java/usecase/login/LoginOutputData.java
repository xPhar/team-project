package usecase.login;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;
    private final String userType;
    private final Object[][] assignments;

    public LoginOutputData(String username, String userRole, Object[][] assignments) {
        this.username = username;
        this.userType = userRole;
        this.assignments = assignments;
    }

    public String getUsername() {
        return username;
    }

    public String getUserType() {
        return userType;
    }

    public Object[][] getAssignments() {
        return assignments;
    }
}
