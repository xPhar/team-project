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
    public void execute(GradeInputData inputData) {
        try {
            final double grade = Double.parseDouble(inputData.getGrade());
            final double maxGrade = 100;

            if (grade < 0) {
                throw new InvalidGradeException("Grade must be greater than 0!");
            }
            if (grade > maxGrade) {
                throw new InvalidGradeException(
                        String.format("Grade must be less than %.1f!", maxGrade)
                );
            }

            gradeDataAccessInterface.grade(
                    inputData.getSubmitter(),
                    grade,
                    inputData.getFeedback()
            );

            gradeOutputBoundary.prepareGradeSuccessView();
        }
        catch (NumberFormatException ex) {
            gradeOutputBoundary.prepareGradeFailureView("Grade must be a number!");
        }
        catch (InvalidGradeException ex) {
            gradeOutputBoundary.prepareGradeFailureView(ex.getMessage());
        }
    }

    private static final class InvalidGradeException extends Exception {
        InvalidGradeException(String message) {
            super(message);
        }
    }
}
