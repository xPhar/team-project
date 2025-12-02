package usecase.Assignments;

import java.util.List;

public class AssignmentsOutputData {
    private final List<AssignmentDTO> assignments;
    private final String courseName;
    private final boolean isInstructor;

    // For switching to submission list view
    private final String assignmentName;
    private final String[][] submissions;

    public AssignmentsOutputData(List<AssignmentDataTransferObject> assignments, String courseName, boolean isInstructor) {
        this.assignments = assignments;
        this.courseName = courseName;
        this.isInstructor = isInstructor;
        this.assignmentName = null;
        this.submissions = null;
    }

    public AssignmentsOutputData(String assignmentName, String[][] submissions) {
        this.assignmentName = assignmentName;
        this.submissions = submissions;

        assignments = null;
        courseName = null;
        isInstructor = false;
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

    public String getAssignmentName() {
        return assignmentName;
    }

    public String[][] getSubmissions() {
        return submissions;
    }
}
