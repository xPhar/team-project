package usecase.signup;

/**
 * Output Data for the Login Use Case.
 */
public class SignupOutputData {

    private final String username;

    public SignupOutputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}