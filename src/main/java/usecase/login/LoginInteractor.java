package usecase.login;

import data_access.DataAccessException;
import entity.Assignment;
import entity.Submission;
import entity.User;

import java.util.List;

/**
 * The Login Interactor.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    private LoginOutputData prepareOutputData(User user) {
        String username = user.getName();
        String userRole = user.getUserType() == User.INSTRUCTOR ? "instructor" : "student";

        Object[][] assignmentsArray = null;
        if (user.getUserType() == User.STUDENT) {
            List<Assignment> assignments = userDataAccessObject.getAssignments();

            assignmentsArray = new Object[assignments.size()][4];
            for (int i = 0; i < assignments.size(); i++) {
                Assignment assignment = assignments.get(i);

                assignmentsArray[i][0] = assignment.getName();
                assignmentsArray[i][1] = assignment.getDueDate();

                Submission submission = userDataAccessObject.getSubmission(assignment);
                if (submission != null) {
                    Submission.Status status = submission.getStatus();
                    if (status == Submission.GRADED) {
                        assignmentsArray[i][2] = Double.toString(submission.getGrade());
                    } else {
                        assignmentsArray[i][2] = "-";
                    }
                    assignmentsArray[i][3] = "✔";
                } else {
                    assignmentsArray[i][2] = "";
                    assignmentsArray[i][3] = "✖";
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
            return;
        }
        if (password.isEmpty()) {
            loginPresenter.prepareFailView("Password cannot be empty.");
            return;
        }

        if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView("Account '" + username + "' does not exist.");
            return;
        }

        final User user;
        try {
            user = userDataAccessObject.getUser(username);
        } catch (DataAccessException e) {
            loginPresenter.prepareFailView("An error has occurred, please try again.\n" +
                    "Error message: " + e.getMessage());
            return;
        }

        if (!user.getPassword().equals(password)) {
            loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            return;
        }

        userDataAccessObject.setActiveUser(user);

        loginPresenter.prepareSuccessView(prepareOutputData(user));
    }

    @Override
    public void switchToSignupView() {
        loginPresenter.switchToSignupView();
    }
}
