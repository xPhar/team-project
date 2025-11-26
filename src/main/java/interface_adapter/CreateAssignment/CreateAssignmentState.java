package interface_adapter.CreateAssignment;

public class CreateAssignmentState {
    private String errorMessage = "";
    private boolean success = false;

    public CreateAssignmentState() {
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
