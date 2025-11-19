package interface_adapter.submission;

import usecase.Submission.SubmissionInputBoundary;

/**
 * Submission related use case.
 */
public class SubmissionController {
    private final SubmissionInputBoundary submissionInputBoundary;

    public SubmissionController(
            SubmissionInputBoundary submissionInputBoundary
    ) {
        this.submissionInputBoundary = submissionInputBoundary;
    }

    public void executeGrade() {

    }

    public void executeBack() {
        submissionInputBoundary.backToSubmissionList();
    }

    public void executeDownload() {

    }
}
