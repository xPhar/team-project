package usecase.grade;

public interface GradeDataAccessInterface {
    /**
     * Grade the submission.
     * @param submitter the submitter of the submission.
     * @param grade the grade of the submission.
     * @param feedback feedback of the submission.
     */
    void grade(String submitter, double grade, String feedback);
}
