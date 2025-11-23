package interface_adapter.Assignments;

import entity.Assignment;
import java.util.ArrayList;
import java.util.List;

public class AssignmentsState {
    private List<Assignment> assignments = new ArrayList<>();
    private String courseName = "";
    private boolean isInstructor = false;
    private String errorMessage = "";

    public AssignmentsState(AssignmentsState copy) {
        this.assignments = copy.assignments;
        this.courseName = copy.courseName;
        this.isInstructor = copy.isInstructor;
        this.errorMessage = copy.errorMessage;
    }

    public AssignmentsState() {
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
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
