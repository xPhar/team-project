package interfaceadapter.signup;

/**
 * The State for the signup View.
 *
 * <p>The signup view holds a username, password, role, and full name specified by the user.</p>
 */
public class SignupState {
    private String username = "";
    private String password = "";
    private String userRole = "Student"; // Default to Student
    private String fullName = "";
    private String signupError = "";

    // Copy constructor for creating a new state from existing one
    public SignupState() {}

    public SignupState(SignupState copy) {
        this.username = copy.username;
        this.password = copy.password;
        this.userRole = copy.userRole;
        this.fullName = copy.fullName;
        this.signupError = copy.signupError;
    }

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

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "SignupState{" +
                "username='" + username + '\'' +
                ", password='" + (password != null ? "[PROTECTED]" : "null") + '\'' +
                ", userRole='" + userRole + '\'' +
                ", fullName='" + fullName + '\'' +
                ", signupError='" + signupError + '\'' +
                '}';
    }
}