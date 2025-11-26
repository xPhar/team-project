package usecase.Assignments;

import entity.Assignment;
import entity.User;

import java.util.List;

public interface AssignmentsDataAccessInterface {
    List<Assignment> getAssignments(String courseCode);

    User getUser();

    String getCourseCode();
}
