package usecase.Submission;

import entity.Submission;

import java.io.File;

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
    public void backToSubmissionList() {
        submissionOutputBoundary.backToSubmissionListView();
    }

    @Override
    public void downloadFile(File saveFile) {
        submissionDataAccessInterface.saveFile(saveFile);
        submissionOutputBoundary.prepareDownloadSuccessView(String.format("File saved to %s", saveFile.getAbsolutePath()));
    }
}
