package usecase.create_assignment;

public interface CreateAssignmentOutputBoundary {
    void prepareSuccessView(CreateAssignmentOutputData outputData);

    void prepareFailureView(String errorMessage);

    void switchToAssignmentView();
}
