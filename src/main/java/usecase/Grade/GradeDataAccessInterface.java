package usecase.Grade;

import entity.Submission;

public interface GradeDataAccessInterface {
    void grade(String assignment, String submitter, double grade, String feedback);
}
