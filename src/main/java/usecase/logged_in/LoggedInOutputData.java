package usecase.logged_in;

import interface_adapter.submission_list.SubmissionTableModel;

import java.util.List;

public class LoggedInOutputData {
    private final String username;
    private final String assignmentName;
    private final String[][] submissions;
    private final List<String> assignments;

    public LoggedInOutputData(String username, String assignmentName,
                              String[][] submissions, List<String> assignments) {
        this.username = username;
        this.assignmentName = assignmentName;
        this.submissions = submissions;
        this.assignments = assignments;
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
}
