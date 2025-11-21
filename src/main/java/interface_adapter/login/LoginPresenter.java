package interface_adapter.login;

import app.Session;
import entity.User;
import interface_adapter.ViewManagerModel;
//import interface_adapter.logged_in.LoggedInState;
//import interface_adapter.logged_in.LoggedInViewModel;
import usecase.login.LoginOutputBoundary;
import usecase.login.LoginOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class LoginPresenter implements LoginOutputBoundary {
    private final Session session;
    private final LoginViewModel loginViewModel;
//    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoginPresenter(Session session,
                          ViewManagerModel viewManagerModel,
//                          LoggedInViewModel loggedInViewModel,
                          LoginViewModel loginViewModel) {
        this.session = session;
        this.viewManagerModel = viewManagerModel;
//        this.loggedInViewModel = loggedInViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        User user = response.getUser();

        session.setUser(user);


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
}