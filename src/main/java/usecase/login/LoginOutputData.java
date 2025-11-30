package usecase.login;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;
    private final String userRole;
    private final Object[][] assignments;

    public LoginOutputData(String username, String userRole, Object[][] assignments) {
        this.username = username;
        this.userRole = userRole;
        this.assignments = assignments;
    }

    public String getUsername() {
        return username;
    }

    public String getUserRole() {
        return userRole;
    }

    public Object[][] getAssignments() {
        return assignments;
    }
}
