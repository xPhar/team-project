package interface_adapter.logged_in;

import interface_adapter.ViewModel;

/**
 * The View Model for the Logged In View.
 */
public class LoggedInViewModel extends ViewModel<LoggedInState> {

    public static final String TITLE_LABEL = "Welcome to Coursework Platform";
    public static final String LOGOUT_BUTTON_LABEL = "Log out";
    public static final String VIEW_ASSIGNMENTS_LABEL = "View Assignments";
    public static final String CREATE_ASSIGNMENT_LABEL = "Create Assignment";

    public LoggedInViewModel() {
        super("logged in");
        setState(new LoggedInState());
    }
}
