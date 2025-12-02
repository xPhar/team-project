package usecase.LoggedIn;

import java.time.LocalDateTime;
import java.util.List;

public class LoggedInOutputData {
    private final String username;
    private final String assignmentName;
    private final String[][] submissions;
    private final List<String> assignments;
    private final String assignmentDescription;
    private final LocalDateTime assignmentDueDate;

    public LoggedInOutputData(String username, String assignmentName,
                              String[][] submissions, List<String> assignments) {
        this(username, assignmentName, submissions, assignments,
                null, null);
    }

    public LoggedInOutputData(String username, String assignmentName,
                              String[][] submissions, List<String> assignments,
                              String assignmentDescription, LocalDateTime assignmentDueDate) {
        this.username = username;
        this.assignmentName = assignmentName;
        this.submissions = submissions;
        this.assignments = assignments;
        this.assignmentDescription = assignmentDescription;
        this.assignmentDueDate = assignmentDueDate;
    }

    public LoggedInOutputData(String assignmentName, String assignmentDescription,
                              LocalDateTime assignmentDueDate) {
        this(null, assignmentName, null, null,
                assignmentDescription, assignmentDueDate);
    }

    public String getUsername() {
        return username;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public String[][] getSubmissions() {
        return submissions;
    }

    public List<String> getAssignments() {
        return assignments;
    }

    public String getAssignmentDescription() {
        return assignmentDescription;
    }

    public LocalDateTime getAssignmentDueDate() {
        return assignmentDueDate;
    }
}
