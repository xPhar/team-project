package usecase.Grade;

import entity.Submission;

public interface GradeDataAccessInterface {
    void grade(String submitter, double grade, String feedback);
}
