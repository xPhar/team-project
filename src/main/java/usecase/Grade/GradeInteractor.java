package usecase.Grade;

public class GradeInteractor implements GradeInputBoundary {
    private final GradeDataAccessInterface gradeDataAccessInterface;
    private final GradeOutputBoundary gradeOutputBoundary;
    private static final class InvalidGradeException extends Exception {
        public InvalidGradeException(String message) {
            super(message);
        }
    }

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
            double grade = Double.parseDouble(inputData.getGrade());
            final double maxGrade = 100; // Hardcoded

            if (grade < 0) {
                throw new InvalidGradeException("Grade must be greater than 0!");
            }
            if (grade > maxGrade) {
                throw new InvalidGradeException(String.format("Grade must be less than %.1f!", maxGrade));
            }

            gradeDataAccessInterface.grade(
                    inputData.getSubmitter(),
                    grade,
                    inputData.getFeedback()
            );

            gradeOutputBoundary.prepareGradeSuccessView();
        } catch (NumberFormatException e) {
            gradeOutputBoundary.prepareGradeFailureView("Grade must be a number!");
        } catch (InvalidGradeException e){
            gradeOutputBoundary.prepareGradeFailureView(e.getMessage());
        }


    }
}
