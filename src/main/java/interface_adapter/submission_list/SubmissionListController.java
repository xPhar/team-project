package interface_adapter.submission_list;

import usecase.SubmissionList.SubmissionListInputBoundary;

/**
 * Submission list view related use case.
 */
public class SubmissionListController {

    private final SubmissionListInputBoundary submissionListInputBoundary;

    /**
     * Constructs a SubmissionListController with the specified input boundary.
     *
     * @param submissionInputBoundary the input boundary responsible for handling submission list-related use cases
     */
    public SubmissionListController(SubmissionListInputBoundary submissionInputBoundary) {
        this.submissionListInputBoundary = submissionInputBoundary;
    }

    /**
     * Executes the process to handle a submission selection.
     * Typically, triggered when a user interacts with an interface element
     * (e.g., double-clicks on a submission in a table).
     * This method delegates the operation to the input boundary by invoking
     * the {@code getSubmission} method with placeholder arguments.
     */
    public void executeChooseSubmission() {
        submissionListInputBoundary.getSubmission("", "");
    }

    /**
     * TBA.
     */
    public void executeBack() {

    }
}
