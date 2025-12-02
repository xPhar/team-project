package interfaceadapter.submission_list;

import usecase.SubmissionList.SubmissionListInputBoundary;
import usecase.SubmissionList.SubmissionListInputData;

/**
 * Submission list view related use case.
 */
public class SubmissionListController {

    private SubmissionListInputBoundary submissionListInputBoundary;

    public SubmissionListController(SubmissionListInputBoundary submissionInputBoundary) {
        this.submissionListInputBoundary = submissionInputBoundary;
    }

    /**
     * Choose a submission.
     * @param submitter the submitter of the submission.
     * @param assignmentName The assignment name.
     */
    public void executeChooseSubmission(String submitter, String assignmentName) {
        final SubmissionListInputData data = new SubmissionListInputData(false, submitter, assignmentName);
        submissionListInputBoundary.execute(data);
    }

    /**
     * Go back to assignment.
     */
    public void executeBack() {
        final SubmissionListInputData data =
                new SubmissionListInputData(true, "", "");
        submissionListInputBoundary.execute(data);
    }
}
