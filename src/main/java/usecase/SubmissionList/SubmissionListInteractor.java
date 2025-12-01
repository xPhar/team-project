package usecase.SubmissionList;

import entity.Submission;

import java.time.format.DateTimeFormatter;

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
    public void execute(SubmissionListInputData data) {
        if (data.isBack()) {
            submissionListOutputBoundary.goToAssignmentView();
        } else {
            Submission submission = submissionListDataAccessInterface
                    .getSubmissionForSubmissionView(data.getSubmitter());

            SubmissionListOutputData outputData = new SubmissionListOutputData(
                    data.getAssignmentName(),
                    submission.getSubmitter(),
                    submission.getStatus().toString(),
                    submission.getSubmissionTime().format(
                            DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")
                    ),
                    submission.getStatus() == Submission.Status.GRADED ? String.format("%.1f", submission.getGrade()) : "",
                    submission.getFeedback() == null ? "" : submission.getFeedback(),
                    submission.getSubmissionName(),
                    "100" // Hardcoded
            );

            submissionListOutputBoundary.prepareSubmissionView(outputData);
        }
    }
}
