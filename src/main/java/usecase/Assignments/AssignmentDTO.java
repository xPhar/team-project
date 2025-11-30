package usecase.Assignments;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for Assignment data.
 * Used to pass assignment information between layers without exposing the
 * entity.
 */
public class AssignmentDTO {
    private final String name;
    private final String description;
    private final LocalDateTime dueDate;
    private final double gracePeriod;
    private final List<String> supportedFileTypes;

    public AssignmentDTO(String name, String description, LocalDateTime dueDate,
            double gracePeriod, List<String> supportedFileTypes) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.gracePeriod = gracePeriod;
        this.supportedFileTypes = supportedFileTypes;
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
}
