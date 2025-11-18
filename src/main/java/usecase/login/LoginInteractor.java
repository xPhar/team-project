package usecase.login;

import entity.User;

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

        // Input validation
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
        } else {
            final User user = userDataAccessObject.get(username);
            if (user == null) {
                loginPresenter.prepareFailView("Error retrieving user data.");
                return;
            }

            final String storedPassword = user.getPassword();
            if (!password.equals(storedPassword)) {
                loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            } else {
                userDataAccessObject.setCurrentUsername(username);

                final LoginOutputData loginOutputData = new LoginOutputData(user.getName(), user.getClass().getSimpleName());
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }

    @Override
    public void switchToSignupView() {
        loginPresenter.switchToSignupView();
    }
}