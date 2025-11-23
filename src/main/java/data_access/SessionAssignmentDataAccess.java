package data_access;

import app.Session;
import entity.Assignment;
import entity.Course;
import entity.User;
import usecase.Assignments.AssignmentsDataAccessInterface;
import usecase.CreateAssignment.CreateAssignmentDataAccessInterface;
import usecase.EditAssignment.EditAssignmentDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class SessionAssignmentDataAccess
        implements AssignmentsDataAccessInterface, CreateAssignmentDataAccessInterface,
        EditAssignmentDataAccessInterface {
    private final Session session;

    public SessionAssignmentDataAccess(Session session) {
        this.session = session;
    }

    @Override
    public Course getCourse() {
        return session.getCourse();
    }

    @Override
    public User getUser() {
        return session.getUser();
    }

    @Override
    public List<Assignment> getAssignments(String courseCode) {
        Course course = session.getCourse();
        if (course != null && course.getCourseCode().equals(courseCode)) {
            return course.getAssignments();
        }
        return new ArrayList<>();
    }

    @Override
    public void saveAssignment(String courseCode, Assignment assignment) {
        Course course = session.getCourse();
        if (course != null && course.getCourseCode().equals(courseCode)) {
            List<Assignment> assignments = course.getAssignments();
            if (assignments == null) {
                assignments = new ArrayList<>();
                course.setAssignments(assignments);
            }
            // Check if assignment with same name exists and replace it
            boolean replaced = false;
            for (int i = 0; i < assignments.size(); i++) {
                if (assignments.get(i).getName().equals(assignment.getName())) {
                    assignments.set(i, assignment);
                    replaced = true;
                    break;
                }
            }
            if (!replaced) {
                assignments.add(assignment);
            }
        }
    }

    @Override
    public Assignment getAssignment(String courseCode, String assignmentName) {
        Course course = session.getCourse();
        if (course != null && course.getCourseCode().equals(courseCode)) {
            List<Assignment> assignments = course.getAssignments();
            if (assignments != null) {
                for (Assignment assignment : assignments) {
                    if (assignment.getName().equals(assignmentName)) {
                        return assignment;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void deleteAssignment(String courseCode, String assignmentName) {
        Course course = session.getCourse();
        if (course != null && course.getCourseCode().equals(courseCode)) {
            List<Assignment> assignments = course.getAssignments();
            if (assignments != null) {
                assignments.removeIf(assignment -> assignment.getName().equals(assignmentName));
            }
        }
    }
}
