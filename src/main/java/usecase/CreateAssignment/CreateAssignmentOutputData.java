package usecase.CreateAssignment;

import entity.Assignment;

public class CreateAssignmentOutputData {
    private final Assignment assignment;
    private final String message;
    private final boolean success;

    public CreateAssignmentOutputData(Assignment assignment, String message, boolean success) {
        this.assignment = assignment;
        this.message = message;
        this.success = success;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
