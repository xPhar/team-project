package entity;

import java.time.LocalDateTime;
import java.util.List;

public class Assignment {
    private String name;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime dueDate;
    private double gracePeriod;
    private String latePenalty;
    private List<String> supportedFileTypes;

     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public String getDescription() {
         return description;
     }

     public void setDescription(String description) {
         this.description = description;
     }

     public LocalDateTime getCreationDate() {
         return creationDate;
     }

     public void setCreationDate(LocalDateTime creationDate) {
         this.creationDate = creationDate;
     }

     public LocalDateTime getDueDate() {
         return dueDate;
     }

     public void setDueDate(LocalDateTime dueDate) {
         this.dueDate = dueDate;
     }

     public double getGracePeriod() {
         return gracePeriod;
     }

     public void setGracePeriod(double gracePeriod) {
         this.gracePeriod = gracePeriod;
     }

     public String getLatePenalty() {
         return latePenalty;
     }

     public void setLatePenalty(String latePenalty) {
         this.latePenalty = latePenalty;
     }

     public List<String> getSupportedFileTypes() {
         return supportedFileTypes;
     }

     public void setSupportedFileTypes(List<String> supportedFileTypes) {
         this.supportedFileTypes = supportedFileTypes;
     }
}
