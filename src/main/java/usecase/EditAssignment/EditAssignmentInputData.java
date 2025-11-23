package usecase.EditAssignment;

import java.time.LocalDateTime;
import java.util.List;

public class EditAssignmentInputData {
    private final String originalName;
    private final String newName;
    private final String description;
    private final LocalDateTime dueDate;
    private final double gracePeriod;
    private final String latePenalty;
    private final List<String> supportedFileTypes;
    private final String courseCode;

    public EditAssignmentInputData(String originalName, String newName, String description, LocalDateTime dueDate,
            double gracePeriod, String latePenalty, List<String> supportedFileTypes, String courseCode) {
        this.originalName = originalName;
        this.newName = newName;
        this.description = description;
        this.dueDate = dueDate;
        this.gracePeriod = gracePeriod;
        this.latePenalty = latePenalty;
        this.supportedFileTypes = supportedFileTypes;
        this.courseCode = courseCode;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getNewName() {
        return newName;
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

    public String getCourseCode() {
        return courseCode;
    }
}
