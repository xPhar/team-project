package usecase.edit_assignment;

public interface EditAssignmentOutputBoundary {
    void prepareSuccessView();

    void prepareFailureView(String error);
}
