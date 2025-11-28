package usecase.Submission;

import entity.Submission;

public interface SubmissionOutputBoundary {
    void backToSubmissionListView(SubmissionOutputData data);
    void prepareDownloadSuccessView(String msg);
    void prepareDownloadFailureView(String msg);
}
