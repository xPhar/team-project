package interface_adapter.logged_in;

import interface_adapter.ViewManagerModel;

public class LoggedInController {

    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;

    public LoggedInController(ViewManagerModel viewManagerModel,
                              LoggedInViewModel loggedInViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    public void openAssignment(String assignmentName) {
        String role = loggedInViewModel.getState().getUserType();

        if ("instructor".equals(role)) {
            viewManagerModel.setState("SubmissionList");
        } else {
            viewManagerModel.setState("Submit");
        }
        viewManagerModel.firePropertyChange();
    }

    public void showDistribution() {
        viewManagerModel.setState("average");
        viewManagerModel.firePropertyChange();
    }

    public void createAssignment() {
        viewManagerModel.setState("Assignments");
        viewManagerModel.firePropertyChange();
    }

    public void logout() {
        viewManagerModel.setState("login");
        viewManagerModel.firePropertyChange();
    }

    public void goBack() {
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}

