package interface_adapter.EditAssignment;

import entity.Assignment;

public class EditAssignmentState {
    private Assignment assignment = new Assignment();
    private String originalName = "";
    private String errorMessage = null;
    private boolean success = false;

    public EditAssignmentState(EditAssignmentState copy) {
        this.assignment = copy.assignment;
        this.originalName = copy.originalName;
        this.errorMessage = copy.errorMessage;
        this.success = copy.success;
    }

    public EditAssignmentState() {
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
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
