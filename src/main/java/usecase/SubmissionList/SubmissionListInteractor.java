package usecase.SubmissionList;

import entity.Submission;

import java.time.format.DateTimeFormatter;
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
    public void execute(SubmissionListInputData data) {
        if (data.isBack()) {
            submissionListOutputBoundary.goToAssignmentView();
        } else {
            Submission submission = submissionListDataAccessInterface
                    .getSubmissionForSubmissionView(data.getSubmitter());

            SubmissionListOutputData outputData = new SubmissionListOutputData(
                    "",
                    submission.getSubmitter(),
                    submission.getStatus().toString(),
                    submission.getSubmissionTime().format(
                            DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")
                    ),
                    String.format("%.1f", submission.getGrade()),
                    submission.getFeedback(),
                    submission.getSubmissionName()
            );

            submissionListOutputBoundary.prepareSubmissionView(outputData);
        }
    }
}
