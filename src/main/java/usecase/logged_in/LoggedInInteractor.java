package usecase.logged_in;

import entity.Assignment;
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
                LoggedInOutputData outputData = new LoggedInOutputData(null, assignment.getName(), null, null);
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
                        userDataAccessObject.getSubmissionTableModel(assignment),
                        null);
                loginPresenter.switchToSubmissionListView(outputData);
            }
        }
    }
}
