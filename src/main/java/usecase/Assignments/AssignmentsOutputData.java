package usecase.Assignments;

import interface_adapter.Assignments.AssignmentDataTransferObject;

import java.util.List;

public class AssignmentsOutputData {
    private final List<AssignmentDataTransferObject> assignments;
    private final String courseName;
    private final boolean isInstructor;

    public AssignmentsOutputData(List<AssignmentDataTransferObject> assignments, String courseName, boolean isInstructor) {
        this.assignments = assignments;
        this.courseName = courseName;
        this.isInstructor = isInstructor;
    }

    public List<AssignmentDataTransferObject> getAssignments() {
        return assignments;
    }

    public String getCourseName() {
        return courseName;
    }

    public boolean isInstructor() {
        return isInstructor;
    }
}
