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
        String role = loggedInViewModel.getState().getRole();

        if ("instructor".equals(role)) {
            viewManagerModel.setState(// TODO: mark assignment state name.);
        } else {
            viewManagerModel.setState("Submit");
            // TODO: resubmit depends on whether submitted?
        }
        viewManagerModel.firePropertyChanged();
    }

    public void showDistribution() {
        viewManagerModel.setState("average");
        viewManagerModel.firePropertyChanged();
    }

    public void createAssignment() {
        viewManagerModel.setState(// TODO: create assignment state name);
        viewManagerModel.firePropertyChanged();
    }
}
