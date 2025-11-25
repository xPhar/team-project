package usecase.EditAssignment;

import java.time.LocalDateTime;
import java.util.List;

public class EditAssignmentInputData {
    private final String name;
    private final String courseCode;

    private final String description;
    private final LocalDateTime dueDate;
    private final List<String> supportedFileTypes;

    public EditAssignmentInputData(String name, String courseCode, String description, LocalDateTime dueDate,
            List<String> supportedFileTypes) {
        this.name = name;
        this.courseCode = courseCode;
        this.description = description;
        this.dueDate = dueDate;
        this.supportedFileTypes = supportedFileTypes;
    }

    public String getName() {
        return name;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public List<String> getSupportedFileTypes() {
        return supportedFileTypes;
    }
}
