package interfaceadapter.submission;

import java.io.File;

import usecase.Grade.GradeInputBoundary;
import usecase.Grade.GradeInputData;
import usecase.Submission.SubmissionInputBoundary;
import usecase.Submission.SubmissionInputData;

/**
 * Submission related use case.
 */
public class SubmissionController {
    private static final int SUBMITTER_SUBSTRING_INDEX = 11;

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
     * Grade the submission.
     * @param grade the grade
     * @param submitter the submitter
     * @param feedback the feedback
     */
    public void executeGrade(String grade, String submitter, String feedback) {
        final String submitterSubstring = submitter.substring(SUBMITTER_SUBSTRING_INDEX);
        final GradeInputData data = new GradeInputData(grade, submitterSubstring, feedback);
        gradeInputBoundary.execute(data);
    }

    /**
     * Go back to submission list.
     */
    public void executeBack() {
        final SubmissionInputData data = new SubmissionInputData(true, null, null);
        submissionInputBoundary.execute(data);
    }

    /**
     * Download the file.
     * @param saveFile the file path to save
     * @param submitter the submitter
     */
    public void executeDownload(File saveFile, String submitter) {
        final String submitterSubstring = submitter.substring(SUBMITTER_SUBSTRING_INDEX);
        final SubmissionInputData data = new SubmissionInputData(false, saveFile, submitterSubstring);
        submissionInputBoundary.execute(data);
    }
}
