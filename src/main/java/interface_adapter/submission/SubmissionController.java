package interface_adapter.submission;

import java.io.File;

import usecase.Grade.GradeInputBoundary;
import usecase.Grade.GradeInputData;
import usecase.Submission.SubmissionInputBoundary;

/**
 * Submission related use case.
 */
public class SubmissionController {
    private final SubmissionInputBoundary submissionInputBoundary;
    private final GradeInputBoundary gradeInputBoundary;

    public SubmissionController(
            SubmissionInputBoundary submissionInputBoundary,
            GradeInputBoundary gradeInputBoundary
    ) {
        this.submissionInputBoundary = submissionInputBoundary;
        this.gradeInputBoundary = gradeInputBoundary;
    }

    /**
     * Processes the grading of a submission by forwarding the provided grade details
     * to the grading input boundary.
     *
     * @param grade The grade assigned to the submission.
     * @param submitter The identifier of the individual who submitted the work.
     * @param feedback Feedback or comments regarding the submitted work.
     */
    public void executeGrade(String grade, String submitter, String feedback) {
        final GradeInputData data = new GradeInputData(grade, submitter, feedback);
        gradeInputBoundary.grade(data);
    }

    /**
     * Navigates back to the submission list.
     * This method delegates the action to the submission input boundary to handle
     * the transition or any required logic for returning to the submission list.
     */
    public void executeBack() {
        submissionInputBoundary.backToSubmissionList();
    }

    /**
     * Initiates the download process for a file.
     *
     * @param saveFile The file
     */
    public void executeDownload(File saveFile) {
        submissionInputBoundary.downloadFile(saveFile);
    }
}
