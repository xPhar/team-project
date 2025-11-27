package usecase.Submission;

import data_access.DataAccessException;
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
    public void downloadFile(SubmissionInputData data) {
        try {
            submissionDataAccessInterface.saveFile(data.getSaveFile(), data.getSubmitter());
            submissionOutputBoundary.prepareDownloadSuccessView(String.format("File saved to %s", data.getSaveFile().getAbsolutePath()));
        }
        catch (DataAccessException e) {
            submissionOutputBoundary.prepareDownloadFailureView(e.getMessage());
        }
    }
}
