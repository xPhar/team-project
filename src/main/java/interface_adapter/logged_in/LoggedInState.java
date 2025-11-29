package interface_adapter.logged_in;

public class LoggedInState {

    private String username;
    private String userType;
    private Object[][] assignments;
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

    public void setUsername(String name) {
        this.username = name;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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
}
