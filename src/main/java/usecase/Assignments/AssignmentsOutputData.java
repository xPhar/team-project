package usecase.Assignments;

import interface_adapter.Assignments.AssignmentDTO;

import java.util.List;

public class AssignmentsOutputData {
    private final List<AssignmentDTO> assignments;
    private final String courseName;
    private final boolean isInstructor;

    public AssignmentsOutputData(List<AssignmentDTO> assignments, String courseName, boolean isInstructor) {
        this.assignments = assignments;
        this.courseName = courseName;
        this.isInstructor = isInstructor;
    }

    public List<AssignmentDTO> getAssignments() {
        return assignments;
    }

    public String getCourseName() {
        return courseName;
    }

    public boolean isInstructor() {
        return isInstructor;
    }
}
