package interface_adapter.login;

import interface_adapter.ViewManagerModel;
//import interface_adapter.logged_in.LoggedInState;
//import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.signup.SignupViewModel;
import usecase.login.LoginOutputBoundary;
import usecase.login.LoginOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class LoginPresenter implements LoginOutputBoundary {

    private final interface_adapter.login.LoginViewModel loginViewModel;
    //    private final LoggedInViewModel loggedInViewModel;
    private final SignupViewModel signupViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
//                          LoggedInViewModel loggedInViewModel,
                          SignupViewModel signupViewModel,
                          LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
//        this.loggedInViewModel = loggedInViewModel;
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
//        // On success, update the loggedInViewModel's state
//        final LoggedInState loggedInState = loggedInViewModel.getState();
//        loggedInState.setUsername(response.getUsername());
//        this.loggedInViewModel.firePropertyChange();
//
//        // and clear everything from the LoginViewModel's state
//        loginViewModel.setState(new LoginState());
//
//        // switch to the logged in view
//        this.viewManagerModel.setState(loggedInViewModel.getViewName());
//        this.viewManagerModel.firePropertyChange();
        throw new Error("LoginPresenter not implemented");
    }

    @Override
    public void prepareFailView(String error) {
//        final LoginState loginState = loginViewModel.getState();
//        loginState.setLoginError(error);
//        loginViewModel.firePropertyChange();
        throw new Error("LoginPresenter not implemented");
    }

    @Override
    public void switchToSignupView() {
        viewManagerModel.setState(signupViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
