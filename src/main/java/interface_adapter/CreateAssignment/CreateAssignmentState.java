package interface_adapter.CreateAssignment;

public class CreateAssignmentState {
    private String errorMessage = "";
    private boolean success;

    public CreateAssignmentState() {
        success = false;
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
