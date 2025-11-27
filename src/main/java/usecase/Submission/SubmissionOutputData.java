package usecase.Submission;

import interface_adapter.submission_list.SubmissionTableModel;

public class SubmissionOutputData {
    private final SubmissionTableModel submissionTableModel;

    public  SubmissionOutputData(
            SubmissionTableModel submissionTableModel
    ) {
        this.submissionTableModel = submissionTableModel;
    }

    public SubmissionTableModel getSubmissionTableModel() {
        return submissionTableModel;
    }
}
