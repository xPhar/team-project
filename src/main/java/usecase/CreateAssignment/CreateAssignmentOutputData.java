package usecase.CreateAssignment;

import java.time.LocalDateTime;

public class CreateAssignmentOutputData {

    private final String courseName;
    private final String assignmentName;
    private final LocalDateTime creationDate;
    private final LocalDateTime dueDate;
    private final String message;

    public CreateAssignmentOutputData(String courseName,
                                      String assignmentName,
                                      LocalDateTime creationDate,
                                      LocalDateTime dueDate,
                                      String message) {
        this.courseName = courseName;
        this.assignmentName = assignmentName;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.message = message;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public String getMessage() {
        return message;
    }
}
