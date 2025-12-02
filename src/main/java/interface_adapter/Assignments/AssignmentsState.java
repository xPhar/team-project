package interface_adapter.Assignments;

import usecase.Assignments.AssignmentDataTransferObject;

import java.util.List;

public class AssignmentsState {
    private List<AssignmentDataTransferObject> assignments;
    private String courseName;
    private boolean isInstructor;
    private String errorMessage;

    public List<AssignmentDataTransferObject> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<AssignmentDataTransferObject> assignments) {
        this.assignments = assignments;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public boolean isInstructor() {
        return isInstructor;
    }

    public void setInstructor(boolean instructor) {
        isInstructor = instructor;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
