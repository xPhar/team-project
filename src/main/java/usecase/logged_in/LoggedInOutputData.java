package usecase.logged_in;

import interface_adapter.submission_list.SubmissionTableModel;

import java.util.List;

public class LoggedInOutputData {
    private final String username;
    private final String assignmentName;
    private final SubmissionTableModel submissionTableModel;
    private final List<String> assignments;

    public LoggedInOutputData(String username, String assignmentName,
                              SubmissionTableModel submissionTableModel, List<String> assignments) {
        this.username = username;
        this.assignmentName = assignmentName;
        this.submissionTableModel = submissionTableModel;
        this.assignments = assignments;
    }

    public String getUsername() {
        return username;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public SubmissionTableModel getSubmissionTableModel() {
        return submissionTableModel;
    }

    public List<String> getAssignments() {
        return assignments;
    }
}
