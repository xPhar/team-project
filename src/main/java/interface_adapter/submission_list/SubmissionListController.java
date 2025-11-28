package interface_adapter.submission_list;

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

    public void executeChooseSubmission(String submitter, String assignmentName) {
        SubmissionListInputData data = new SubmissionListInputData(false, submitter, assignmentName);
        submissionListInputBoundary.execute(data);
    }

    public void executeBack() {
        SubmissionListInputData data = new SubmissionListInputData(true,"", "");
        submissionListInputBoundary.execute(data);
    }
}
