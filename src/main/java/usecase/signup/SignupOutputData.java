package usecase.signup;

/**
 * Output Data for the Signup Use Case.
 */
public class SignupOutputData {

    private final String username;
    private final String userRole;
    private final String fullName;

    public SignupOutputData(String username) {
        this.username = username;
        this.userRole = "";
        this.fullName = "";
    }

    public SignupOutputData(String username, String userRole, String fullName) {
        this.username = username;
        this.userRole = userRole;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getUserRole() {
        return userRole;
    }

    public String getFullName() {
        return fullName;
    }
}