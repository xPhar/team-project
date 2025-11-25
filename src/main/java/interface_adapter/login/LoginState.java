package interface_adapter.login;

/**
 * The State for a note.
 *
 * <p>For this example, a note is simply a string.</p>
 */
public class LoginState {
    private String username = "";
    private String password = "";
    private String loginError;

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

    public void setLoginError(String errorMessage) {
        this.loginError = errorMessage;
    }

    public String getLoginError() {
        return loginError;
    }

}
