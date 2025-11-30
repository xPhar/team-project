package usecase.login;

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

    @Override
    public void execute(LoginInputData loginInputData) {
        final String username = loginInputData.getUsername().trim();
        final String password = loginInputData.getPassword();

        if (username.isEmpty()) {
            loginPresenter.prepareFailView("Username cannot be empty.");
            return;
        }

        if (password == null || password.isEmpty()) {
            loginPresenter.prepareFailView("Password cannot be empty.");
            return;
        }

        if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView("Account '" + username + "' does not exist.");
            return;
        }

        final User user = userDataAccessObject.getUser(username);
        if (user == null) {
            loginPresenter.prepareFailView("Error retrieving user data.");
            return;
        }

        if (!user.getPassword().equals(password)) {
            loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            return;
        }

        userDataAccessObject.setCurrentUsername(username);
        userDataAccessObject.setActiveUser(user);

        Object[][] assignmentsArray = null;
        if (user.getUserType() == User.STUDENT) {
            List<Assignment> assignments = userDataAccessObject.getAssignments();
            if (assignments == null) {
                assignments = List.of();
            }

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

        String userRole = user.getUserType() == User.INSTRUCTOR ? "instructor" : "student";
        loginPresenter.prepareSuccessView(new LoginOutputData(username, userRole, assignmentsArray));
    }

    @Override
    public void switchToSignupView() {
        loginPresenter.switchToSignupView();
    }
}
