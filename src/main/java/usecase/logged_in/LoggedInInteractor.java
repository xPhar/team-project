package usecase.logged_in;

import entity.Assignment;
import entity.Submission;
import entity.User;

import java.time.format.DateTimeFormatter;
import java.util.List;

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
            LoggedInOutputData outputData = new LoggedInOutputData(userDataAccessObject.getCurrentUsername(), null, null, null);

            userDataAccessObject.resetSession();

            loginPresenter.switchToLoginView(outputData);
        }
        else if (loggedInInputData.getAssignment() == null) {
            if (userDataAccessObject.getUserType() == User.STUDENT) {
                LoggedInOutputData outputData = new LoggedInOutputData(
                        null, null, null, userDataAccessObject.getAllAssignmentNames());

                loginPresenter.switchToClassAverageView(outputData);
            }
            else
                loginPresenter.switchToCreateAssignmentView();
        }
        else {
            Assignment assignment = userDataAccessObject.getAssignment(loggedInInputData.getAssignment());
            userDataAccessObject.setActiveAssignment(assignment);

            if (userDataAccessObject.getUserType() == User.STUDENT) {
                LoggedInOutputData outputData = new LoggedInOutputData(assignment.getName(), assignment.getDescription(),
                        assignment.getDueDate());
                if (userDataAccessObject.userHasSubmitted(assignment)) {
                    loginPresenter.switchToResubmitView(outputData);
                }
                else {
                    loginPresenter.switchToSubmitView(outputData);
                }
            }
            else if (userDataAccessObject.getUserType() == User.INSTRUCTOR) {
                LoggedInOutputData outputData = new LoggedInOutputData(
                        userDataAccessObject.getCurrentUsername(),
                        assignment.getName(),
                        getSubmissionText(assignment),
                        null);
                loginPresenter.switchToSubmissionListView(outputData);
            }
        }
    }

    private String[][] getSubmissionText(Assignment assignment) {
        List<Submission> submissions = userDataAccessObject.getSubmissionList(assignment);
        String[][] submissionText = new String[submissions.size()][];
        for (int i = 0; i < submissions.size(); i++) {
            Submission submission = submissions.get(i);

            String gradeString = "pending";
            Submission.Status status = submission.getStatus();
            if (status == Submission.Status.GRADED) {
                gradeString = String.format("%.1f", submission.getGrade());
            }

            submissionText[i] = new String[]{
                    submission.getSubmitter(),
                    submission.getSubmissionTime().format(
                            DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")
                    ),
                    gradeString
            };
        }
        return submissionText;
    }
}
