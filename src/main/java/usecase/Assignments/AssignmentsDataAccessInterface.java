package usecase.Assignments;

import entity.Assignment;
import entity.Submission;
import entity.User;

import java.util.List;

public interface AssignmentsDataAccessInterface {
    List<Assignment> getAssignments();

    User getCurrentUser();

    String getCourseCode();

    List<Submission> getSubmissionList(Assignment assignment);

    Assignment getAssignment(String assignment);

    void setActiveAssignment(Assignment assignment);

    void resetSession();
}
