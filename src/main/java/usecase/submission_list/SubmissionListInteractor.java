package usecase.submission_list;

import java.time.format.DateTimeFormatter;

import entity.Submission;

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
        }
        else {
            final Submission submission = submissionListDataAccessInterface
                    .getSubmissionForSubmissionView(data.getSubmitter());

            String gradeString = "";
            if (submission.getStatus() == Submission.Status.GRADED) {
                gradeString = String.format("%.1f", submission.getGrade());
            }

            final SubmissionListOutputData outputData = new SubmissionListOutputData(
                    data.getAssignmentName(),
                    submission.getSubmitter(),
                    submission.getStatus().toString(),
                    submission.getSubmissionTime().format(
                            DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")
                    ),
                    gradeString,
                    submission.getFeedback(),
                    submission.getSubmissionName(),
                    "100"
            );

            submissionListOutputBoundary.prepareSubmissionView(outputData);
        }
    }
}
