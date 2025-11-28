package interface_adapter.logged_in;

public class LoggedInState {

    private String username = "";
    private String userType = "";
    private Object[][] assignments = new Object[0][0];
    private String welcomeMessage = "";

    public LoggedInState() {
    }

    public LoggedInState(LoggedInState copy) {
        this.username = copy.username;
        this.userType = copy.userType;
        this.assignments = copy.assignments;
        this.welcomeMessage = copy.welcomeMessage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
        updateWelcomeMessage();
    }

    public String getUserType() {
        return userType;
    }

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
        return "LoggedInState{" +
                "username='" + username + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
