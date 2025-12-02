package interface_adapter.signup;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import usecase.signup.SignupOutputBoundary;
import usecase.signup.SignupOutputData;

/**
 * The Presenter for the Signup Use Case.
 */
public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupViewModel;
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;

    public SignupPresenter(ViewManagerModel viewManagerModel,
                           SignupViewModel signupViewModel,
                           LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(SignupOutputData response) {
        // On success, switch to login view and pre-fill the username
        final LoginState loginState = loginViewModel.getState();
        loginState.setUsername(response.getUsername());
        loginViewModel.setState(loginState);
        loginViewModel.firePropertyChange();

        // Clear the signup form
        signupViewModel.setState(new SignupState());

        // Switch to the login view
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        final SignupState signupState = signupViewModel.getState();
        signupState.setSignupError(error);
        signupViewModel.firePropertyChange();
    }

    @Override
    public void switchToLoginView() {
        signupViewModel.setState(new SignupState());
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
