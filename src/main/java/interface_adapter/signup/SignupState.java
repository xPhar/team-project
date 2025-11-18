package interface_adapter.signup;

/**
 * The State for the signup View.
 *
 * <p>The signup view holds a username and a password specified by the user.</p>
 */
public class SignupState {
    private String username = "";
    private String password = "";
    private String signupError;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSignupError(String errorMessage) {
        this.signupError = errorMessage;
    }

    public String getSignupError() {
        return signupError;
    }

}
