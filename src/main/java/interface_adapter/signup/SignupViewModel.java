package interface_adapter.signup;

import interface_adapter.ViewModel;

/**
 * The View Model for the Signup View.
 */
public class SignupViewModel extends ViewModel<SignupState> {

    public SignupViewModel() {
        super("signup");
        setState(new SignupState());
    }

}
