package interface_adapter.EditAssignment;

import java.time.LocalDateTime;
import java.util.List;

public class EditAssignmentState {
    private final String courseCode;
    private final String name;
    private final String description;
    private final LocalDateTime dueDate;
    private final double gracePeriod;
    private final List<String> supportedFileTypes;
    private String errorMessage;
    private boolean success;

    public EditAssignmentState(String courseCode, String name, String description,
            LocalDateTime dueDate, double gracePeriod, List<String> supportedFileTypes) {

        success = false;
        errorMessage = null;
        this.courseCode = courseCode;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.gracePeriod = gracePeriod;
        this.supportedFileTypes = supportedFileTypes;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getName() {
        return name;
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

    public List<String> getSupportedFileTypes() {
        return supportedFileTypes;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
