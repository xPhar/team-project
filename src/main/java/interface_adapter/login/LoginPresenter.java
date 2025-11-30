package interface_adapter.login;

import interface_adapter.Assignments.AssignmentsViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.signup.SignupViewModel;
import usecase.login.LoginOutputBoundary;
import usecase.login.LoginOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final AssignmentsViewModel assignmentsViewModel;
    private final SignupViewModel signupViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          LoggedInViewModel loggedInViewModel,
                          AssignmentsViewModel assignmentsViewModel,
                          SignupViewModel signupViewModel,
                          LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.assignmentsViewModel = assignmentsViewModel;
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        loginViewModel.setState(new LoginState());

        if ("instructor".equalsIgnoreCase(response.getUserRole())) {
            viewManagerModel.setState(assignmentsViewModel.getViewName());
        } else {
            final LoggedInState loggedInState = loggedInViewModel.getState();

            loggedInState.setUsername(response.getUsername());
            loggedInState.setUserType(response.getUserRole());
            loggedInState.setAssignments(response.getAssignments());
            loggedInViewModel.setState(loggedInState);
            loggedInViewModel.firePropertyChange();

            viewManagerModel.setState(loggedInViewModel.getViewName());
        }
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.firePropertyChange();
    }

    @Override
    public void switchToSignupView() {
        loginViewModel.setState(new LoginState());
        viewManagerModel.setState(signupViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
