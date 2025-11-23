package usecase.CreateAssignment;

import entity.Assignment;

public class CreateAssignmentInputData {
    private final Assignment assignment;
    private final String courseCode;

    public CreateAssignmentInputData(Assignment assignment, String courseCode) {
        this.assignment = assignment;
        this.courseCode = courseCode;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public String getCourseCode() {
        return courseCode;
    }
}
