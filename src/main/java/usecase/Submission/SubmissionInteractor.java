package usecase.Submission;

import entity.Submission;

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
    public void getSubmission(String assignmentName, String submitter) {
        Submission submission = submissionDataAccessInterface
                .getSubmission(assignmentName, submitter);

        submissionOutputBoundary.prepareSubmissionView(submission);
    }
}
