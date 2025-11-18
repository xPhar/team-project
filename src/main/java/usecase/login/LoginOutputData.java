package usecase.login;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;
    private final String userRole;

    public LoginOutputData(String username, String userRole) {
        this.username = username;
        this.userRole = userRole;
    }

    public String getUsername() {
        return username;
    }

    public String getUserRole() {
        return userRole;
    }
}