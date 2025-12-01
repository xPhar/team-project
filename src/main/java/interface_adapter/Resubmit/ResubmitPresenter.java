package interface_adapter.Resubmit;

import interface_adapter.Submit.SubmitViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import usecase.Resubmit.ResubmitOutputBoundary;

import java.awt.*;

public class ResubmitPresenter implements ResubmitOutputBoundary {

    private final ViewManagerModel manager;
    private final ResubmitViewModel resubmitViewModel;
    private final SubmitViewModel submitViewModel;
    private final LoggedInViewModel loggedInViewModel;

    public ResubmitPresenter(ViewManagerModel vmm,
                             ResubmitViewModel resubmitViewModel,
                             SubmitViewModel submitViewModel,
                             LoggedInViewModel lo) {
        this.manager = vmm;
        this.resubmitViewModel = resubmitViewModel;
        this.submitViewModel = submitViewModel;
        loggedInViewModel = lo;
    }

    @Override
    public void prepareSuccessView() {
        switchToSubmitView();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        ResubmitState resubmitState = new ResubmitState();
        resubmitState.setMessage(errorMessage);
        resubmitState.setMsgColor(Color.RED);
        resubmitViewModel.setState(resubmitState);
        resubmitViewModel.firePropertyChange();
    }

    @Override
    public void switchToSubmitView() {
        manager.setState(submitViewModel.getViewName());
        manager.firePropertyChange();
    }

    @Override
    public void switchToLogginView() {
        manager.setState(loggedInViewModel.getViewName());
        manager.firePropertyChange();
    }
}
