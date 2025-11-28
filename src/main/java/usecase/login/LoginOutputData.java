package usecase.login;

/**
 * The Input Data for the Login Use Case.
 */
public class LoginOutputData {
    private final String username;
    private final String userType;
    private final Object[][] assignments;

    public LoginOutputData(String username, String userType, Object[][] assignments) {
        this.username = username;
        this.userType = userType;
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
