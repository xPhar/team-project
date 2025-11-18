package interface_adapter.signup;

import interface_adapter.ViewManagerModel;
//import interface_adapter.logged_in.LoggedInState;
//import interface_adapter.logged_in.LoggedInViewModel;
import usecase.signup.SignupOutputBoundary;
import usecase.signup.SignupOutputData;

/**
 * The Presenter for the Signup Use Case.
 */
public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupViewModel;
    //    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    public SignupPresenter(ViewManagerModel viewManagerModel,
//                          LoggedInViewModel loggedInViewModel,
                           SignupViewModel signupViewModel) {
        this.viewManagerModel = viewManagerModel;
//        this.loggedInViewModel = loggedInViewModel;
        this.signupViewModel = signupViewModel;
    }

    @Override
    public void prepareSuccessView(SignupOutputData response) {
//        // On success, update the loggedInViewModel's state
//        final LoggedInState loggedInState = loggedInViewModel.getState();
//        loggedInState.setUsername(response.getUsername());
//        this.loggedInViewModel.firePropertyChange();
//
//        // and clear everything from the SignupViewModel's state
//        signupViewModel.setState(new SignupState());
//
//        // switch to the logged in view
//        this.viewManagerModel.setState(loggedInViewModel.getViewName());
//        this.viewManagerModel.firePropertyChange();
        throw new Error("SignupPresenter not implemented");
    }

    @Override
    public void prepareFailView(String error) {
//        final SignupState signupState = signupViewModel.getState();
//        signupState.setSignupError(error);
//        signupViewModel.firePropertyChange();
        throw new Error("SignupPresenter not implemented");
    }
}
