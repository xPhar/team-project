package usecase.login;

/**
 * The Input Data for the Login Use Case.
 */
public class LoginOutputData {
    private final String username;
    private final Object[][] assignments;

    public LoginOutputData(String username, Object[][] assignments) {
        this.username = username;
        this.assignments = assignments;
    }

    public String getUsername() {
        return username;
    }

    public Object[][] getAssignments() {
        return assignments;
    }
}
