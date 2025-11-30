package usecase.signup;

import entity.User;
import entity.Student;
import entity.Instructor;

/**
 * The Signup Interactor.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary presenter;

    public SignupInteractor(SignupDataAccessInterface userDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.presenter = signupOutputBoundary;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        final String username = signupInputData.getUsername().trim();
        final String password = signupInputData.getPassword();
        final String userRole = signupInputData.getUserRole();
        final String fullName = signupInputData.getFullName().trim();

        // Validate input
        if (username.isEmpty()) {
            presenter.prepareFailView("Username cannot be empty.");
            return;
        }

        if (username.length() < 3) {
            presenter.prepareFailView("Username must be at least 3 characters long.");
            return;
        }

        if (password == null || password.length() < 6) {
            presenter.prepareFailView("Password must be at least 6 characters long.");
            return;
        }

        if (fullName.isEmpty()) {
            presenter.prepareFailView("Full name cannot be empty.");
            return;
        }

        if (userRole == null || (!userRole.equals("Student") && !userRole.equals("Instructor"))) {
            presenter.prepareFailView("Invalid user role. Must be 'Student' or 'Instructor'.");
            return;
        }

        // Check for duplicate username
        if (userDataAccessObject.existsByName(username)) {
            presenter.prepareFailView("Username '" + username + "' already exists. Please choose a different username.");
            return;
        }

        try {
            // Create the appropriate user type - using basic 2-parameter constructors
            User newUser;
            if ("Instructor".equals(userRole)) {
                newUser = new Instructor(username, password);
            } else {
                newUser = new Student(username, password);
            }

            // Save the new user
            userDataAccessObject.save(newUser);

            // Set as current user
            userDataAccessObject.setCurrentUsername(username);

            // Prepare success response
            final SignupOutputData signupOutputData = new SignupOutputData(username, userRole, fullName);
            presenter.prepareSuccessView(signupOutputData);

        } catch (Exception e) {
            presenter.prepareFailView("Registration failed: " + e.getMessage());
        }
    }

    @Override
    public void switchToLoginView() {
        presenter.switchToLoginView();
    }
}
