package usecase.Grade;

public interface GradeOutputBoundary {
    void prepareGradeSuccessView();
    void prepareGradeFailureView(String msg);
}
