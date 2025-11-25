package usecase.logged_in;

import entity.Assignment;

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

            LoggedInOutputData outputData = new LoggedInOutputData(userDataAccessObject.getUsername(), null, null);

            loginPresenter.switchToLoginView(outputData);
        }
        else {
            Assignment assignment = userDataAccessObject.getAssignment(loggedInInputData.getAssignment());
            userDataAccessObject.setActiveAssignment(assignment);

            switch (loggedInInputData.getUserType()) {
                case "student" -> {
                    LoggedInOutputData outputData = new LoggedInOutputData(null, assignment.getName(), null);
                    if (loggedInInputData.getSubmitted()) {
                        loginPresenter.switchToResubmitView(outputData);
                    }
                    else {
                        loginPresenter.switchToSubmitView(outputData);
                    }
                }
                case "instructor" -> {
                    LoggedInOutputData outputData = new LoggedInOutputData(
                            userDataAccessObject.getUsername(),
                            assignment.getName(),
                            userDataAccessObject.getSubmissionTableModel(assignment));
                    loginPresenter.switchToSubmissionListView(outputData);
                }
            }
        }
    }
}
