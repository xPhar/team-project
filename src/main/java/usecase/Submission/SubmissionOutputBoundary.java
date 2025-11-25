package usecase.Submission;

import entity.Submission;

public interface SubmissionOutputBoundary {
    void backToSubmissionListView();
    void prepareDownloadSuccessView(String msg);
    void prepareDownloadFailureView(String msg);
}
