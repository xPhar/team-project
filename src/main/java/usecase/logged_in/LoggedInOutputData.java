package usecase.logged_in;

import interface_adapter.submission_list.SubmissionTableModel;

public class LoggedInOutputData {
    private final String username;
    private final String assignmentName;
    private final SubmissionTableModel submissionTableModel;

    public LoggedInOutputData(String username, String assignmentName, SubmissionTableModel submissionTableModel) {
        this.username = username;
        this.assignmentName = assignmentName;
        this.submissionTableModel = submissionTableModel;
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
}
