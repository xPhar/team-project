package usecase.Submission;

import interface_adapter.submission_list.SubmissionTableModel;

public class SubmissionOutputData {
    private final String[][] submissions;

    public  SubmissionOutputData(
            String[][] submissions
    ) {
        this.submissions = submissions;
    }

    public String[][] getSubmissions() {
        return submissions;
    }
}
