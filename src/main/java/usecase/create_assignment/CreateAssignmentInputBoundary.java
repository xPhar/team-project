package usecase.create_assignment;

public interface CreateAssignmentInputBoundary {
    void execute(CreateAssignmentInputData inputData);

    void switchToAssignmentView();
}
