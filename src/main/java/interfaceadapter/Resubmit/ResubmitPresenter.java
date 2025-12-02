package interfaceadapter.Resubmit;

import java.awt.Color;

import interfaceadapter.Submit.SubmitViewModel;
import interfaceadapter.ViewManagerModel;
import interfaceadapter.LoggedIn.LoggedInViewModel;
import usecase.Resubmit.ResubmitOutputBoundary;

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
