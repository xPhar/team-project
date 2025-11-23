package interface_adapter.submission;

import usecase.Grade.GradeInputBoundary;
import usecase.Grade.GradeInputData;
import usecase.Submission.SubmissionInputBoundary;

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
        GradeInputData data = new GradeInputData(grade, submitter, feedback);
        gradeInputBoundary.grade(data);
    }

    public void executeBack() {
        submissionInputBoundary.backToSubmissionList();
    }

    public void executeDownload(File saveFile) {
        submissionInputBoundary.downloadFile(saveFile);
    }
}
