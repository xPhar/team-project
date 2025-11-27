package interface_adapter.submission_list;

import usecase.SubmissionList.SubmissionListInputBoundary;

/**
 * Submission list view related use case.
 */
public class SubmissionListController {

    private SubmissionListInputBoundary submissionListInputBoundary;

    public SubmissionListController(SubmissionListInputBoundary submissionInputBoundary) {
        this.submissionListInputBoundary = submissionInputBoundary;
    }

    public void executeChooseSubmission() {
        submissionListInputBoundary.getSubmission("", "");
    }

    public void executeBack() {
        submissionListInputBoundary.backToAssignment();
    }
}
