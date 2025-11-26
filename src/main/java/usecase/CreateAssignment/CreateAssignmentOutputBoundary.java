package usecase.CreateAssignment;

public interface CreateAssignmentOutputBoundary {
    void prepareSuccessView(CreateAssignmentOutputData outputData);

    void prepareFailureView(String errorMessage);

    void switchToAssignmentView();
}
