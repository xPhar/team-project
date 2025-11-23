package usecase.Assignments;

import entity.Assignment;
import entity.Course;
import entity.User;

import java.util.List;

public interface AssignmentsDataAccessInterface {
    Course getCourse();

    User getUser();

    List<Assignment> getAssignments(String courseCode);
}
