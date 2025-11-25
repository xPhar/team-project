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

            loginPresenter.switchToLoginView();
        }
        else {
            Assignment assignment = userDataAccessObject.getAssignment(loggedInInputData.getAssignment());
            userDataAccessObject.setActiveAssignment(assignment);

            switch (loggedInInputData.getUserType()) {
                case "student" -> {
                    if (loggedInInputData.getSubmitted()) {
                        loginPresenter.switchToResubmitView();
                    }
                    else {
                        loginPresenter.switchToSubmitView();
                    }
                }
                case "instructor" -> loginPresenter.switchToSubmissionListView();
            }
        }
    }
}
