package usecase.EditAssignment;

public interface EditAssignmentOutputBoundary {
    void prepareSuccessView();

    void prepareFailureView(String error);
}
