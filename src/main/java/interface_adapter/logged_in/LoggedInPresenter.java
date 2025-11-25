package interface_adapter.logged_in;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import usecase.logged_in.LoggedInOutputBoundary;

/**
 * The Presenter for the LoggedIn Use Case.
 */
public class LoggedInPresenter implements LoggedInOutputBoundary {
    private final LoggedInViewModel loginViewModel;
    private final Submit loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoggedInPresenter(ViewManagerModel viewManagerModel,
//                          LoggedInViewModel loggedInViewModel,
                             LoggedInViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
//        this.loggedInViewModel = loggedInViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void switchToLoginView() {

    }

    @Override
    public void switchToSubmitView() {

    }

    @Override
    public void switchToSubmissionListView() {

    }

    @Override
    public void prepareFailView(String error) {
//        final LoggedInState loginState = loginViewModel.getState();
//        loginState.setLoggedInError(error);
//        loginViewModel.firePropertyChange();
        throw new Error("LoggedInPresenter not implemented");
    }
}