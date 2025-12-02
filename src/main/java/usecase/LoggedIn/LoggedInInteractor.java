package usecase.LoggedIn;

import java.time.format.DateTimeFormatter;
import java.util.List;

import entity.Assignment;
import entity.Submission;
import entity.User;

/**
 * The Logged In Interactor.
 */
public class LoggedInInteractor implements LoggedInInputBoundary {
    private final LoggedInDataAccessInterface userDataAccessObject;
    private final LoggedInOutputBoundary loginPresenter;

    public LoggedInInteractor(LoggedInDataAccessInterface userDataAccessInterface,
                              LoggedInOutputBoundary loggedInOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loggedInOutputBoundary;
    }

    @Override
    public void execute(LoggedInInputData loggedInInputData) {
        if (loggedInInputData.getLogout()) {
            // Create output data BEFORE resetting the session (or else username is null...)
            final LoggedInOutputData outputData = new LoggedInOutputData(userDataAccessObject.getCurrentUsername(),
                    null, null, null);

            userDataAccessObject.resetSession();

            loginPresenter.switchToLoginView(outputData);
        }
        else if (loggedInInputData.getAssignment() == null) {
            if (userDataAccessObject.getUserType() == User.STUDENT) {
                final LoggedInOutputData outputData = new LoggedInOutputData(null, null, null,
                        userDataAccessObject.getAllAssignmentNames());

                loginPresenter.switchToClassAverageView(outputData);
            }
            else {
                loginPresenter.switchToCreateAssignmentView();
            }
        }
        else {
            final Assignment assignment = userDataAccessObject.getAssignment(loggedInInputData.getAssignment());
            userDataAccessObject.setActiveAssignment(assignment);

            if (userDataAccessObject.getUserType() == User.STUDENT) {
                final LoggedInOutputData outputData = new LoggedInOutputData(assignment.getName(),
                        assignment.getDescription(),
                        assignment.getDueDate());
                if (userDataAccessObject.userHasSubmitted(assignment)) {
                    loginPresenter.switchToResubmitView(outputData);
                }
                else {
                    loginPresenter.switchToSubmitView(outputData);
                }
            }
            else if (userDataAccessObject.getUserType() == User.INSTRUCTOR) {
                final LoggedInOutputData outputData = new LoggedInOutputData(
                        userDataAccessObject.getCurrentUsername(),
                        assignment.getName(),
                        getSubmissionText(assignment),
                        null);
                loginPresenter.switchToSubmissionListView(outputData);
            }
        }
    }

    private String[][] getSubmissionText(Assignment assignment) {
        final List<Submission> submissions = userDataAccessObject.getSubmissionList(assignment);
        final String[][] submissionText = new String[submissions.size()][];
        for (int i = 0; i < submissions.size(); i++) {
            final Submission submission = submissions.get(i);

            String gradeString = "pending";
            final Submission.Status status = submission.getStatus();
            if (status == Submission.Status.GRADED) {
                gradeString = String.format("%.1f", submission.getGrade());
            }

            submissionText[i] = new String[]{
                    submission.getSubmitter(),
                    submission.getSubmissionTime().format(
                            DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")
                    ), gradeString,
            };
        }
        return submissionText;
    }
}
