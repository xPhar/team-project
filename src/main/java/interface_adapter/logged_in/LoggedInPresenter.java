package interface_adapter.logged_in;

import interface_adapter.class_average.ClassAverageState;
import interface_adapter.class_average.ClassAverageViewModel;
import interface_adapter.create_assignment.CreateAssignmentViewModel;
import interface_adapter.resubmit.ResubmitState;
import interface_adapter.resubmit.ResubmitViewModel;
import interface_adapter.submit.SubmitState;
import interface_adapter.submit.SubmitViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.submission_list.SubmissionListState;
import interface_adapter.submission_list.SubmissionListViewModel;
import interface_adapter.submission_list.SubmissionTableModel;
import usecase.logged_in.LoggedInOutputBoundary;
import usecase.logged_in.LoggedInOutputData;

/**
 * The Presenter for the LoggedIn Use Case.
 */
public class LoggedInPresenter implements LoggedInOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;
    private final LoginViewModel loginViewModel;
    private final SubmitViewModel submitViewModel;
    private final ResubmitViewModel resubmitViewModel;
    private final SubmissionListViewModel submissionListViewModel;
    private final ClassAverageViewModel classAverageViewModel;
    private final CreateAssignmentViewModel createAssignmentViewModel;

    public LoggedInPresenter(ViewManagerModel viewManagerModel,
                             LoggedInViewModel loggedInViewModel,
                             LoginViewModel loginViewModel,
                             SubmitViewModel submitViewModel,
                             ResubmitViewModel resubmitViewModel,
                             SubmissionListViewModel submissionListViewModel,
                             ClassAverageViewModel classAverageViewModel,
                             CreateAssignmentViewModel createAssignmentViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.loginViewModel = loginViewModel;
        this.submitViewModel = submitViewModel;
        this.resubmitViewModel = resubmitViewModel;
        this.submissionListViewModel = submissionListViewModel;
        this.classAverageViewModel = classAverageViewModel;
        this.createAssignmentViewModel = createAssignmentViewModel;
    }

    @Override
    public void switchToLoginView(LoggedInOutputData response) {
        // On success, update the loginViewModel's state
        final LoginState loginState = loginViewModel.getState();
        loginState.setUsername(response.getUsername());
        loginState.setPassword("");
        loginViewModel.firePropertyChange();

        // and clear everything from LoggedInViewModel's state
        loggedInViewModel.setState(new LoggedInState());

        // switch to the login view
        this.viewManagerModel.setState(loginViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToSubmitView(LoggedInOutputData response) {
        // On success, update the submitViewModel's state
        // Should not use the old state since each assignment subimit page is independent of each other,
        // better to initialize a new one
        final SubmitState newState = new SubmitState();
        newState.setAssignmentName(response.getAssignmentName());
        newState.setAssignmentDescription(response.getAssignmentDescription());
        newState.setDueDate(response.getAssignmentDueDate());

        // set state to submitViewModel
        submitViewModel.setState(newState);
        submitViewModel.firePropertyChange();

        // and clear everything from LoggedInViewModel's state
        loggedInViewModel.setState(new LoggedInState());

        // switch to the submit view
        this.viewManagerModel.setState(submitViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToResubmitView(LoggedInOutputData response) {

        final SubmitState newSubmitState = new SubmitState();
        newSubmitState.setAssignmentName(response.getAssignmentName());
        newSubmitState.setAssignmentDescription(response.getAssignmentDescription());
        newSubmitState.setDueDate(response.getAssignmentDueDate());
        // set state to submitViewModel
        submitViewModel.setState(newSubmitState);
        submitViewModel.firePropertyChange();
        // On success, update the resubmitViewModel's state
        final ResubmitState newState = new ResubmitState();
        // set state to resubmitViewModel
        resubmitViewModel.setState(newState);
        resubmitViewModel.firePropertyChange();

        // and clear everything from LoggedInViewModel's state
        loggedInViewModel.setState(new LoggedInState());

        // switch to the submit view
        this.viewManagerModel.setState(resubmitViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToSubmissionListView(LoggedInOutputData response) {
        // On success, update the submissionListViewModel's state
        final SubmissionListState submissionListState = submissionListViewModel.getState();
        submissionListState.setTitle(response.getAssignmentName());
        submissionListState.setTableModel(new SubmissionTableModel(response.getSubmissions()));
        submissionListViewModel.firePropertyChange();

        // and clear everything from LoggedInViewModel's state
        loggedInViewModel.setState(new LoggedInState());

        // switch to the submissionList view
        this.viewManagerModel.setState(submissionListViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToClassAverageView(LoggedInOutputData response) {
        // Update classAverageViewModel's state
        final ClassAverageState classAverageState = classAverageViewModel.getState();
        classAverageState.setAssignmentNames(response.getAssignments());
        classAverageViewModel.firePropertyChange();

        // and clear everything from LoggedInViewModel's state
        loggedInViewModel.setState(new LoggedInState());

        // switch to the submissionList view
        this.viewManagerModel.setState(classAverageViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToCreateAssignmentView() {
        // Clear everything from LoggedInViewModel's state
        loggedInViewModel.setState(new LoggedInState());

        // switch to the submissionList view
        this.viewManagerModel.setState(createAssignmentViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        final LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setError(error);
        loginViewModel.firePropertyChange();
    }
}
