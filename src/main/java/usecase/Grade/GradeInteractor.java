package usecase.Grade;

public class GradeInteractor implements GradeInputBoundary {
    private final GradeDataAccessInterface gradeDataAccessInterface;
    private final GradeOutputBoundary gradeOutputBoundary;

    public GradeInteractor(
            GradeDataAccessInterface gradeDataAccessInterface,
            GradeOutputBoundary gradeOutputBoundary
    ) {
        this.gradeDataAccessInterface = gradeDataAccessInterface;
        this.gradeOutputBoundary = gradeOutputBoundary;
    }

    @Override
    public void grade(GradeInputData inputData) {
        try {
            double grade = Double.parseDouble(inputData.getGrade());

            gradeDataAccessInterface.grade(
                    "Assignment",
                    inputData.getSubmitter(),
                    grade,
                    inputData.getFeedback()
            );

            gradeOutputBoundary.prepareGradeSuccessView();
        } catch (NumberFormatException e) {
            gradeOutputBoundary.prepareGradeFailureView("Grade must be a number!");
        }


    }
}
