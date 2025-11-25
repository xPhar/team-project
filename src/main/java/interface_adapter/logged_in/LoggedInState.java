package interface_adapter.logged_in;

public class LoggedInState {

    private String name;
    private String role;
    private Object[][] assignments;

    public LoggedInState() {
        this.name = "";
        this.role = "";
        this.assignments = new Object[0][0];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Object[][] getAssignments() {
        return assignments;
    }

    public void setAssignments(Object[][] assignments) {
        this.assignments = assignments;
    }
}
