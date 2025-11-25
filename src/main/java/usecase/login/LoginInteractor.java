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
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();
        if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView(username + ": Account does not exist.");
        }
        else {
            final User user = userDataAccessObject.getUser(username);

            if (!user.getPassword().equals(password)) {
                loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            }
            else {
                userDataAccessObject.setActiveUser(user);

                String userType;
                if (user.getUserType() == User.STUDENT) {
                    userType = "student";
                }
                else {  // UserType == user.INSTRUCTOR
                    userType = "instructor";
                }

                List<Assignment> assignments = userDataAccessObject.getAssignments();

                Object[][] assignmentsArray = new Object[assignments.size()][4];
                for (int i = 0; i < assignments.size(); i++) {
                    Assignment assignment = assignments.get(i);

                    assignmentsArray[i][0] = assignment.getName();
                    assignmentsArray[i][1] = assignment.getDueDate();

                    Submission submission = userDataAccessObject.getSubmission(assignment);
                    if (submission != null) {
                        Submission.Status status = submission.getStatus();
                        if (status == Submission.GRADED) {
                            assignmentsArray[i][2] = Double.toString(submission.getGrade());
                        }
                        else {
                            assignmentsArray[i][2] = "-";
                        }
                        assignmentsArray[i][3] = "✔";
                    }
                    else {
                        assignmentsArray[i][2] = "";
                        assignmentsArray[i][3] = "✖";
                    }
                }

                loginPresenter.switchToLoggedInView(new LoginOutputData(username, userType, assignmentsArray));
            }
        }
    }
}
