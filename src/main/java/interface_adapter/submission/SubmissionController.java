package interface_adapter.submission;

import usecase.SubmissionList.SubmissionListInputBoundary;
import usecase.SubmissionList.SubmissionListInteractor;

/**
 * Submission related use case.
 */
public class SubmissionController {
    private final SubmissionListInputBoundary submissionListInputBoundary;

    public SubmissionController(
            SubmissionListInputBoundary submissionListInputBoundary
    ) {
        this.submissionListInputBoundary = submissionListInputBoundary;
    }

    public void executeGrade() {

    }

    public void executeBack() {
        submissionListInputBoundary.backToSubmissionList();
    }

    public void executeDownload() {

    }
}
