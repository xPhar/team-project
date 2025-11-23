package usecase.Assignments;

import entity.Assignment;

import java.util.List;

public class AssignmentsOutputData {
    private final List<Assignment> assignments;
    private final String courseName;
    private final boolean isInstructor;

    public AssignmentsOutputData(List<Assignment> assignments, String courseName, boolean isInstructor) {
        this.assignments = assignments;
        this.courseName = courseName;
        this.isInstructor = isInstructor;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public String getCourseName() {
        return courseName;
    }

    public boolean isInstructor() {
        return isInstructor;
    }
}
