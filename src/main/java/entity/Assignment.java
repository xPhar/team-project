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

     public static AssignmentBuilder builder() {
         return new AssignmentBuilder();
     }

    public static class AssignmentBuilder {
        private String name;
        private String description;
        private LocalDateTime creationDate;
        private LocalDateTime dueDate;
        private double gracePeriod;
        private List<String> supportedFileTypes;

        AssignmentBuilder() {}

        public AssignmentBuilder name(String name) {
            this.name = name;
            return this;
        }

        public AssignmentBuilder description(String description) {
            this.description = description;
            return this;
        }

        public AssignmentBuilder creationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public AssignmentBuilder dueDate(LocalDateTime dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public AssignmentBuilder gracePeriod(double gracePeriod) {
            this.gracePeriod = gracePeriod;
            return this;
        }

        public AssignmentBuilder supportedFileTypes(List<String> supportedFileTypes) {
            this.supportedFileTypes = supportedFileTypes;
            return this;
        }

        public Assignment build() {
            return new Assignment(
                    this.name,
                    this.description,
                    this.creationDate,
                    this.dueDate,
                    this.gracePeriod,
                    this.supportedFileTypes
            );
        }
    }
}
