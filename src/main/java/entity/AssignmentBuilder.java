package entity;

import java.time.LocalDateTime;
import java.util.List;

public class AssignmentBuilder {
    private final Assignment assignment;

    public AssignmentBuilder() {
        this.assignment = new Assignment();
    }

    public AssignmentBuilder name(String name) {
        this.assignment.setName(name);
        return this;
    }

    public AssignmentBuilder description(String description) {
        this.assignment.setDescription(description);
        return this;
    }

    public AssignmentBuilder creationDate(LocalDateTime creationDate) {
        this.assignment.setCreationDate(creationDate);
        return this;
    }

    public AssignmentBuilder dueDate(LocalDateTime dueDate) {
        this.assignment.setDueDate(dueDate);
        return this;
    }

    public AssignmentBuilder gracePeriod(double gracePeriod) {
        this.assignment.setGracePeriod(gracePeriod);
        return this;
    }

    public AssignmentBuilder latePenalty(String latePenalty) {
        this.assignment.setLatePenalty(latePenalty);
        return this;
    }

    public AssignmentBuilder supportedFileTypes(List<String> supportedFileTypes) {
        this.assignment.setSupportedFileTypes(supportedFileTypes);
        return this;
    }

    public Assignment build() {
        return this.assignment;
    }
}
