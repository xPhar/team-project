package usecase.grade;

public interface GradeOutputBoundary {
    /**
     * Show the grade successful message.
     */
    void prepareGradeSuccessView();

    /**
     * Show the grade failure message.
     * @param msg the failure message.
     */
    void prepareGradeFailureView(String msg);
}
