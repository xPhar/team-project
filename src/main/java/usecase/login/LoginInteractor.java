package usecase.login;

import java.util.List;

import data_access.DataAccessException;
import entity.Assignment;
import entity.Submission;
import entity.User;

/**
 * The Login Interactor.
 */
public class LoginInteractor implements LoginInputBoundary {
    private static final int ASSIGNMENT_ARRAY_COLS = 4;
    private static final int ASSIGNMENT_NAME_COL = 0;
    private static final int ASSIGNMENT_DUE_DATE_COL = 1;
    private static final int SUBMISSION_GRADE_COL = 2;
    private static final int SUBMISSION_STATUS_COL = 3;

    private final LoginDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    private LoginOutputData prepareOutputData(User user) {
        final String username = user.getName();
        final String userRole;
        if (user.getUserType() == User.INSTRUCTOR) {
            userRole = "instructor";
        }
        else {
            userRole = "student";
        }

        Object[][] assignmentsArray = null;
        if (user.getUserType() == User.STUDENT) {
            final List<Assignment> assignments = userDataAccessObject.getAssignments();

            assignmentsArray = new Object[assignments.size()][ASSIGNMENT_ARRAY_COLS];
            for (int i = 0; i < assignments.size(); i++) {
                final Assignment assignment = assignments.get(i);

                assignmentsArray[i][ASSIGNMENT_NAME_COL] = assignment.getName();
                assignmentsArray[i][ASSIGNMENT_DUE_DATE_COL] = assignment.getDueDate();

                final Submission submission = userDataAccessObject.getSubmission(assignment);
                if (submission != null) {
                    final Submission.Status status = submission.getStatus();
                    if (status == Submission.GRADED) {
                        assignmentsArray[i][SUBMISSION_GRADE_COL] = Double.toString(submission.getGrade());
                    }
                    else {
                        assignmentsArray[i][SUBMISSION_GRADE_COL] = "-";
                    }
                    assignmentsArray[i][SUBMISSION_STATUS_COL] = "✔";
                }
                else {
                    assignmentsArray[i][SUBMISSION_GRADE_COL] = "";
                    assignmentsArray[i][SUBMISSION_STATUS_COL] = "✖";
                }
            }
        }

        return new LoginOutputData(username, userRole, assignmentsArray);
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        final String username = loginInputData.getUsername().trim();
        final String password = loginInputData.getPassword().trim();

        if (username.isEmpty()) {
            loginPresenter.prepareFailView("Username cannot be empty.");
        }
        else if (password.isEmpty()) {
            loginPresenter.prepareFailView("Password cannot be empty.");
        }

        else if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView("Account '" + username + "' does not exist.");
        }

        else {
            try {
                final User user;
                user = userDataAccessObject.getUser(username);

                if (!user.getPassword().equals(password)) {
                    loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
                }
                else {
                    userDataAccessObject.setActiveUser(user);

                    loginPresenter.prepareSuccessView(prepareOutputData(user));
                }
            }
            catch (DataAccessException ex) {
                loginPresenter.prepareFailView("An error has occurred, please try again.\n"
                                             + "Error message: " + ex.getMessage());
            }
        }
    }

    @Override
    public void switchToSignupView() {
        loginPresenter.switchToSignupView();
    }
}
