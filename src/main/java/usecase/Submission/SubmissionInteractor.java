package usecase.Submission;

import data_access.DataAccessException;
import entity.Submission;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SubmissionInteractor implements SubmissionInputBoundary {
    private final SubmissionOutputBoundary submissionOutputBoundary;
    private final SubmissionDataAccessInterface submissionDataAccessInterface;

    public SubmissionInteractor(
            SubmissionOutputBoundary submissionOutputBoundary,
            SubmissionDataAccessInterface submissionDataAccessInterface
    ) {
        this.submissionOutputBoundary = submissionOutputBoundary;
        this.submissionDataAccessInterface = submissionDataAccessInterface;
    }

    @Override
    public void execute(SubmissionInputData data) {
        if (data.isBack()) {
            String[][] submissionText = getSubmissionText();
            SubmissionOutputData outputData = new SubmissionOutputData(submissionText);
            submissionOutputBoundary.backToSubmissionListView(outputData);
        }
        else {
            try {
                submissionDataAccessInterface.saveFile(data.getSaveFile(), data.getSubmitter());
                submissionOutputBoundary.prepareDownloadSuccessView(String.format("File saved to %s", data.getSaveFile().getAbsolutePath()));
            }
            catch (DataAccessException e) {
                submissionOutputBoundary.prepareDownloadFailureView(e.getMessage());
            }
        }
    }

    private String[][] getSubmissionText() {
        List<Submission> submissions = submissionDataAccessInterface.getSubmissionList();
        String[][] submissionText = new String[submissions.size()][];
        for (int i = 0; i < submissions.size(); i++) {
            Submission submission = submissions.get(i);

            String gradeString = "pending";
            Submission.Status status = submission.getStatus();
            if (status == Submission.Status.GRADED) {
                gradeString = String.format("%.1f", submission.getGrade());
            }

            submissionText[i] = new String[]{
                submission.getSubmitter(),
                submission.getSubmissionTime().format(
                        DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")
                ),
                gradeString
            };
        }
        return submissionText;
    }
}
