package interface_adapter.logged_in;

/**
 * The State for the logged-in View.
 */
public class LoggedInState {
    private String username = "";
    private String userRole = "";
    private String welcomeMessage = "";

    public LoggedInState() {}

    public LoggedInState(LoggedInState copy) {
        this.username = copy.username;
        this.userRole = copy.userRole;
        this.welcomeMessage = copy.welcomeMessage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
        updateWelcomeMessage();
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    private void updateWelcomeMessage() {
        if (username != null && !username.isEmpty()) {
            String roleDisplay = "Student";
            if ("Instructor".equals(userRole)) {
                roleDisplay = "Instructor";
            }
            this.welcomeMessage = "Welcome, " + username + "! (" + roleDisplay + ")";
        }
    }

    @Override
    public String toString() {
        return "LoggedInState{" +
                "username='" + username + '\'' +
                ", userRole='" + userRole + '\'' +
                ", welcomeMessage='" + welcomeMessage + '\'' +
                '}';
    }
}
