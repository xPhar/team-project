package interface_adapter.Resubmit;

import interface_adapter.Submit.SubmitViewModel;
import interface_adapter.ViewManagerModel;
import usecase.Resubmit.ResubmitOutputBoundary;

import java.awt.*;

public class ResubmitPresenter implements ResubmitOutputBoundary {

    private final ViewManagerModel manager;
    private final ResubmitViewModel resubmitViewModel;
    private final SubmitViewModel submitViewModel;

    public ResubmitPresenter(ViewManagerModel vmm,
                                   ResubmitViewModel resubmitViewModel,
                                   SubmitViewModel submitViewModel) {
        this.manager = vmm;
        this.resubmitViewModel = resubmitViewModel;
        this.submitViewModel = submitViewModel;
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

}
