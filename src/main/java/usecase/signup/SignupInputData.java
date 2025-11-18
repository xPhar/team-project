package usecase.signup;

/**
 * The Input Data for the Signup Use Case.
 */
public class SignupInputData {

    private final String username;
    private final String password;
    private final String userRole;
    private final String fullName;

    public SignupInputData(String username, String password, String userRole, String fullName) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserRole() {
        return userRole;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return "SignupInputData{" +
                "username='" + username + '\'' +
                ", password='" + (password != null ? "[PROTECTED]" : "null") + '\'' +
                ", userRole='" + userRole + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
