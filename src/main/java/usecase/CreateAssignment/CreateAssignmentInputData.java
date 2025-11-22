package usecase.CreateAssignment;

import java.time.LocalDateTime;
import java.util.List;

public class CreateAssignmentInputData {

    private final String courseName;
    private final String assignmentName;
    private final String description;
    private final LocalDateTime dueDate;
    private final double gracePeriod;
    private final String latePenalty;
    private final List<String> supportedFileTypes;

    public CreateAssignmentInputData(String courseName,
                                     String assignmentName,
                                     String description,
                                     LocalDateTime dueDate,
                                     double gracePeriod,
                                     String latePenalty,
                                     List<String> supportedFileTypes) {
        this.courseName = courseName;
        this.assignmentName = assignmentName;
        this.description = description;
        this.dueDate = dueDate;
        this.gracePeriod = gracePeriod;
        this.latePenalty = latePenalty;
        this.supportedFileTypes = supportedFileTypes;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public double getGracePeriod() {
        return gracePeriod;
    }

    public String getLatePenalty() {
        return latePenalty;
    }

    public List<String> getSupportedFileTypes() {
        return supportedFileTypes;
    }
}

