package usecase.Assignments;

import entity.Assignment;
import entity.User;

import java.util.List;

public interface AssignmentsDataAccessInterface {
    List<Assignment> getAssignments();

    User getCurrentUser();

    String getCourseCode();
}
