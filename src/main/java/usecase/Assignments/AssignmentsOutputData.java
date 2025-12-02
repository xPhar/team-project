package usecase.Assignments;

import java.util.List;

public class AssignmentsOutputData {
    private final List<AssignmentDTO> assignments;
    private final String courseName;
    private final boolean isInstructor;

    // For switching to submission list view
    private final String assignmentName;
    private final String[][] submissions;

    // For switching to login view
    private final String username;

    public AssignmentsOutputData(List<AssignmentDTO> assignments, String courseName, boolean isInstructor) {
        this.assignments = assignments;
        this.courseName = courseName;
        this.isInstructor = isInstructor;
        this.assignmentName = null;
        this.submissions = null;
        this.username = null;
    }

    public AssignmentsOutputData(String assignmentName, String[][] submissions) {
        this.assignmentName = assignmentName;
        this.submissions = submissions;

        assignments = null;
        courseName = null;
        isInstructor = false;
        username = null;
    }

    public AssignmentsOutputData(String username) {
        this.username = null;

        assignments = null;
        courseName = null;
        isInstructor = false;
        assignmentName = null;
        submissions = null;
    }

    public String getUsername() {
        return username;
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
