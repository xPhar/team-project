package usecase.CreateAssignment;

public interface CreateAssignmentInputBoundary {
    void execute(CreateAssignmentInputData inputData);

    void switchToAssignmentView();
}
