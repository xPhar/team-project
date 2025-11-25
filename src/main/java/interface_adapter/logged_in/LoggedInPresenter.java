package interface_adapter.logged_in;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import usecase.logged_in.LoggedInOutputBoundary;

/**
 * The Presenter for the LoggedIn Use Case.
 */
public class LoggedInPresenter implements LoggedInOutputBoundary {
    private final LoggedInViewModel loginViewModel;
//    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoggedInPresenter(ViewManagerModel viewManagerModel,
//                          LoggedInViewModel loggedInViewModel,
                             LoggedInViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
//        this.loggedInViewModel = loggedInViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void switchToLoggedInView() {

//        // On success, update the loggedInViewModel's state
//        final LoggedInState loggedInState = loggedInViewModel.getState();
//        loggedInState.setUsername(response.getUsername());
//        this.loggedInViewModel.firePropertyChange();
//
//        // and clear everything from the LoggedInViewModel's state
//        loginViewModel.setState(new LoggedInState());
//
//        // switch to the logged in view
//        this.viewManagerModel.setState(loggedInViewModel.getViewName());
//        this.viewManagerModel.firePropertyChange();
        throw new Error("LoggedInPresenter not implemented");
    }

    @Override
    public void prepareFailView(String error) {
//        final LoggedInState loginState = loginViewModel.getState();
//        loginState.setLoggedInError(error);
//        loginViewModel.firePropertyChange();
        throw new Error("LoggedInPresenter not implemented");
    }
}