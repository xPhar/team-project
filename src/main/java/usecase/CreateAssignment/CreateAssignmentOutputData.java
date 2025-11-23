package usecase.CreateAssignment;

<<<<<<< HEAD
import entity.Assignment;

public class CreateAssignmentOutputData {
    private final Assignment assignment;
    private final String message;
    private final boolean success;

    public CreateAssignmentOutputData(Assignment assignment, String message, boolean success) {
        this.assignment = assignment;
        this.message = message;
        this.success = success;
    }

    public Assignment getAssignment() {
        return assignment;
=======
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
>>>>>>> b9a7cc9bb1bdc914e096b401146dd992396e05d2
    }

    public String getMessage() {
        return message;
    }
<<<<<<< HEAD

    public boolean isSuccess() {
        return success;
    }
=======
>>>>>>> b9a7cc9bb1bdc914e096b401146dd992396e05d2
}
