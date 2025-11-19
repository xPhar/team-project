package usecase.SubmissionList;

import entity.Submission;

import java.util.List;

public class SubmissionListInteractor implements SubmissionListInputBoundary {
    private final SubmissionListOutputBoundary submissionListOutputBoundary;
    private final SubmissionListDataAccessInterface submissionListDataAccessInterface;

    public SubmissionListInteractor(
            SubmissionListOutputBoundary submissionListOutputBoundary,
            SubmissionListDataAccessInterface submissionListDataAccessInterface
    ) {
        this.submissionListOutputBoundary = submissionListOutputBoundary;
        this.submissionListDataAccessInterface = submissionListDataAccessInterface;
    }

    @Override
    public void getSubmissionList(String assignmentName) {
        List<Submission> submissions =
                submissionListDataAccessInterface.getSubmissionList(assignmentName);

        SubmissionListOutputData outputData =
                new SubmissionListOutputData(assignmentName, submissions);

        submissionListOutputBoundary.prepareListView(outputData);
    }

    @Override
    public void getSubmission(String assignmentName, String submitter) {
        Submission submission = submissionListDataAccessInterface
                .getSubmission(assignmentName, submitter);

        submissionListOutputBoundary.prepareSubmissionView(submission);
    }

    @Override
    public void backToAssignment() {

    }
}
