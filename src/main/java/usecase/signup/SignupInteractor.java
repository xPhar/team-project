package usecase.signup;

import entity.User;

/**
 * The  Interactor.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary Presenter;

    public SignupInteractor(SignupDataAccessInterface userDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.Presenter = signupOutputBoundary;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        final String username = signupInputData.getUsername();
        final String password = signupInputData.getPassword();
        if (!userDataAccessObject.existsByName(username)) {
            Presenter.prepareFailView(username + ": Account does not exist.");
        }
        else {
            final String pwd = userDataAccessObject.get(username).getPassword();
            if (!password.equals(pwd)) {
                Presenter.prepareFailView("Incorrect password for \"" + username + "\".");
            }
            else {

                final User user = userDataAccessObject.get(signupInputData.getUsername());

                userDataAccessObject.setCurrentUsername(username);

                final SignupOutputData signupOutputData = new SignupOutputData(user.getName());
                Presenter.prepareSuccessView(signupOutputData);
            }
        }
    }
}