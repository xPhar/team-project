package usecase.Assignments;

public interface AssignmentsOutputBoundary {
    void prepareSuccessView(AssignmentsOutputData outputData);

    void prepareFailureView(String errorMessage);
}
