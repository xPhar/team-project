package usecase.Submission;

import data_access.DataAccessException;
import entity.Submission;
import interface_adapter.submission_list.SubmissionTableModel;

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
    public void execute(SubmissionInputData data) {
        if (data.isBack()) {
            SubmissionTableModel tableModel = submissionDataAccessInterface.getSubmissionTableModelForCurrentAssignment();
            SubmissionOutputData outputData = new SubmissionOutputData(tableModel);
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
}
