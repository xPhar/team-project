package interface_adapter.CreateAssignment;

public class CreateAssignmentState {
    private String courseName = "";
    private String errorMessage = "";
    private boolean success = false;

    public CreateAssignmentState(CreateAssignmentState copy) {
        this.courseName = copy.courseName;
        this.errorMessage = copy.errorMessage;
        this.success = copy.success;
    }

    public CreateAssignmentState() {
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
