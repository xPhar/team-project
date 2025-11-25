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
            userDataAccessObject.resetSession();

            LoggedInOutputData outputData = new LoggedInOutputData(userDataAccessObject.getCurrentUsername(), null, null);

            loginPresenter.switchToLoginView(outputData);
        }
        else {
            Assignment assignment = userDataAccessObject.getAssignment(loggedInInputData.getAssignment());
            userDataAccessObject.setActiveAssignment(assignment);



            if (userDataAccessObject.getUserType() == User.STUDENT) {
                LoggedInOutputData outputData = new LoggedInOutputData(null, assignment.getName(), null);
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
                        userDataAccessObject.getSubmissionTableModel(assignment));
                loginPresenter.switchToSubmissionListView(outputData);
            }
        }
    }
}
