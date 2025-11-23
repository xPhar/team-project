package usecase.EditAssignment;

public interface EditAssignmentOutputBoundary {
    void prepareSuccessView(EditAssignmentOutputData outputData);

    void prepareFailureView(String error);
}
