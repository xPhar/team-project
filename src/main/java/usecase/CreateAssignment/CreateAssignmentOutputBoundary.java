package usecase.CreateAssignment;

public interface CreateAssignmentOutputBoundary {

    void prepareSuccessView(CreateAssignmentOutputData outputData);

    void prepareFailView(String errorMessage);
}
