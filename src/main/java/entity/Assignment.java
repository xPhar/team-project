package entity;

import java.time.LocalDateTime;
import java.util.List;

public class Assignment {
    private String name;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime dueDate;
    private double gracePeriod;
    private List<String> supportedFileTypes;

    public Assignment(String name, String description, LocalDateTime creationDate, LocalDateTime dueDate,
            double gracePeriod, List<String> supportedFileTypes) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
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

    /**
     * Creates a new instance of the AssignmentBuilder, which can be used to construct
     * an Assignment object with specified properties.
     *
     * @return a new instance of AssignmentBuilder
     */
    public static AssignmentBuilder builder() {
        return new AssignmentBuilder();
    }

    /**
     * A builder class for creating instances of the Assignment class. This builder allows
     * step-by-step specification of properties such as the assignment's name, description,
     * creation date, due date, grace period, and supported file types before constructing
     * the final Assignment object.
     */
    public static class AssignmentBuilder {
        private String name;
        private String description;
        private LocalDateTime creationDate;
        private LocalDateTime dueDate;
        private double gracePeriod;
        private List<String> supportedFileTypes;

        AssignmentBuilder() {
        }

        /**
         * Sets the name of the assignment to be built.
         *
         * @param newName the name of the assignment to be built
         * @return the builder instance for method chaining
         */
        public AssignmentBuilder name(String newName) {
            name = newName;
            return this;
        }

        /**
         * Sets the description of the assignment to be built.
         *
         * @param newDescription the description of the assignment to be built
         * @return the builder instance for method chaining
         */
        public AssignmentBuilder description(String newDescription) {
            description = newDescription;
            return this;
        }

        /**
         * Sets the creation date of the assignment to be built.
         *
         * @param newCreationDate the creation date of the assignment to be built
         * @return the builder instance for method chaining
         */
        public AssignmentBuilder creationDate(LocalDateTime newCreationDate) {
            creationDate = newCreationDate;
            return this;
        }

        /**
         * Sets the due date for the assignment being built.
         *
         * @param newDueDate the due date of the assignment to be built
         * @return the builder instance for method chaining
         */
        public AssignmentBuilder dueDate(LocalDateTime newDueDate) {
            dueDate = newDueDate;
            return this;
        }

        /**
         * Sets the grace period for the assignment being built.
         *
         * @param newGracePeriod the grace period (in days) to allow after the due date
         *                       for submitting the assignment without penalty
         * @return the builder instance for method chaining
         */
        public AssignmentBuilder gracePeriod(double newGracePeriod) {
            gracePeriod = newGracePeriod;
            return this;
        }

        /**
         * Sets the supported file types for the assignment being built.
         *
         * @param newSupportedFileTypes the list of supported file types for the assignment
         * @return the builder instance for method chaining
         */
        public AssignmentBuilder supportedFileTypes(List<String> newSupportedFileTypes) {
            supportedFileTypes = newSupportedFileTypes;
            return this;
        }

        /**
         * Builds and returns a new instance of the Assignment class using the properties
         * set in the AssignmentBuilder.
         *
         * @return a new Assignment instance with the specified name, description,
         * creation date, due date, grace period, and supported file types
         */
        public Assignment build() {
            return new Assignment(
                    this.name,
                    this.description,
                    this.creationDate,
                    this.dueDate,
                    this.gracePeriod,
                    this.supportedFileTypes);
        }
    }
}
