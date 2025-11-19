package usecase.Submission;

import entity.Submission;

public class SubmissionInteractor implements SubmissionInputBoundary {
    private final SubmissionOutputBoundary submissionOutputBoundary;

    public SubmissionInteractor(
            SubmissionOutputBoundary submissionOutputBoundary
    ) {
        this.submissionOutputBoundary = submissionOutputBoundary;
    }

    @Override
    public void backToSubmissionList() {
        submissionOutputBoundary.backToSubmissionListView();
    }
}
