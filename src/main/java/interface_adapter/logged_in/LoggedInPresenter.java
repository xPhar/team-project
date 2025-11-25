package interface_adapter.logged_in;

import interface_adapter.Resubmit.ResubmitViewModel;
import interface_adapter.Submit.SubmitViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import usecase.logged_in.LoggedInOutputBoundary;

/**
 * The Presenter for the LoggedIn Use Case.
 */
public class LoggedInPresenter implements LoggedInOutputBoundary {
    private final LoggedInViewModel loginViewModel;
    private final SubmitViewModel submitViewModel;
    private final ResubmitViewModel resubmitViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoggedInPresenter(ViewManagerModel viewManagerModel,
                             SubmitViewModel loggedInViewModel,
                             ResubmitViewModel resubmitViewModel,
                             LoggedInViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.submitViewModel = loggedInViewModel;
        this.resubmitViewModel = resubmitViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void switchToLoginView() {

    }

    @Override
    public void switchToSubmitView() {

    }

    @Override
    public void switchToResubmitView() {

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