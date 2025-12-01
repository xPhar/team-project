package interface_adapter.Submit;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import usecase.Submit.SubmitOutputBoundary;
import usecase.Submit.SubmitOutputData;

import java.awt.*;

public class SubmitPresenter implements SubmitOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final SubmitViewModel submitViewModel;
    private final LoggedInViewModel loggedInViewModel;

    public SubmitPresenter(ViewManagerModel viewManagerModel,
                           SubmitViewModel submitViewModel, LoggedInViewModel loggedInViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.submitViewModel = submitViewModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void prepareSuccessView(SubmitOutputData submitOutputData) {
        SubmitState newSubmitState = submitViewModel.getState();
        newSubmitState.setMessage(submitOutputData.getOutputMsg());
        newSubmitState.setMsgColor(Color.GREEN);
        submitViewModel.setState(newSubmitState);
        submitViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailureView(SubmitOutputData submitOutputData) {
        SubmitState newSubmitState = submitViewModel.getState();
        String errorMsg = submitOutputData.getOutputMsg();
        newSubmitState.setMessage(errorMsg);
        newSubmitState.setMsgColor(Color.RED);
        submitViewModel.setState(newSubmitState);
        submitViewModel.firePropertyChange();
    }

    /**
     *
     */
    @Override
    public void switchToLoggedInView() {
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
