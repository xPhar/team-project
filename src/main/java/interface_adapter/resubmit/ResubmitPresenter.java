package interface_adapter.resubmit;

import java.awt.Color;

import interface_adapter.submit.SubmitViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import usecase.resubmit.ResubmitOutputBoundary;

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
        final ResubmitState resubmitState = resubmitViewModel.getState();
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
