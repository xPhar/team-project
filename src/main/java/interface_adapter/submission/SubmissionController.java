package interface_adapter.submission;

import usecase.Grade.GradeInputBoundary;
import usecase.Grade.GradeInputData;
import usecase.Submission.SubmissionInputBoundary;
import usecase.Submission.SubmissionInputData;

import java.io.File;

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

    public void executeGrade(String grade, String submitter, String feedback) {
        submitter = submitter.substring(11);
        GradeInputData data = new GradeInputData(grade, submitter, feedback);
        gradeInputBoundary.execute(data);
    }

    public void executeBack() {
        SubmissionInputData data = new SubmissionInputData(true, null, null);
        submissionInputBoundary.execute(data);
    }

    public void executeDownload(File saveFile, String submitter) {
        submitter = submitter.substring(11);
        SubmissionInputData data = new SubmissionInputData(false, saveFile, submitter);
        submissionInputBoundary.execute(data);
    }
}
