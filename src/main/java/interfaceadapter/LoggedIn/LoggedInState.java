package interfaceadapter.LoggedIn;

public class LoggedInState {

    private String username;
    private String userType;
    private Object[][] assignments;
    private String welcomeMessage = "";
    private String error;

    public LoggedInState() {
        this.username = "";
        this.userType = "";
        this.assignments = new Object[0][0];
        this.error = "";
    }

    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for this user.
     *
     * @param name the new username to assign
     */
    public void setUsername(String name) {
        this.username = name;
        updateWelcomeMessage();
    }

    public String getUserType() {
        return userType;
    }

    /**
     * Sets the type for this user (STUDENT/INSTRUCTOR).
     *
     * @param userType the type of this user
     */
    public void setUserType(String userType) {
        this.userType = userType;
        updateWelcomeMessage();
    }

    public Object[][] getAssignments() {
        return assignments;
    }

    public void setAssignments(Object[][] assignments) {
        this.assignments = assignments;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    private void updateWelcomeMessage() {
        if (username != null && !username.isEmpty()) {
            String roleDisplay = "Student";
            if ("instructor".equalsIgnoreCase(userType)) {
                roleDisplay = "Instructor";
            }
            this.welcomeMessage = "Welcome, " + username + "! (" + roleDisplay + ")";
        }
    }

    @Override
    public String toString() {
        return "LoggedInState{"
                + "username='" + username + '\''
                + ", userType='" + userType + '\''
                + '}';
    }
}
