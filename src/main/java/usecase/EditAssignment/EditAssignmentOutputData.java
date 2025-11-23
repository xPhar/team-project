package usecase.EditAssignment;

import entity.Assignment;

public class EditAssignmentOutputData {
    private final Assignment assignment;
    private final String courseCode;
    private final boolean useCaseFailed;

    public EditAssignmentOutputData(Assignment assignment, String courseCode, boolean useCaseFailed) {
        this.assignment = assignment;
        this.courseCode = courseCode;
        this.useCaseFailed = useCaseFailed;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
