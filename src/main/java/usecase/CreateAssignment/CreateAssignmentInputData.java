package usecase.CreateAssignment;

import java.time.LocalDateTime;
import java.util.List;

public class CreateAssignmentInputData {
    private final String name;
    private final String description;
    private final LocalDateTime dueDate;
    private final double gracePeriod;
    private final List<String> supportedFileTypes;
    private final String courseCode;

    public CreateAssignmentInputData(String name, String description, LocalDateTime dueDate,
            double gracePeriod, List<String> supportedFileTypes, String courseCode) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.gracePeriod = gracePeriod;
        this.supportedFileTypes = supportedFileTypes;
        this.courseCode = courseCode;
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

    public String getCourseCode() {
        return courseCode;
    }
}
