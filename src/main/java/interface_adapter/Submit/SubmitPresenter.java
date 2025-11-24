package interface_adapter.Submit;

import usecase.Submit.SubmitOutputBoundary;
import usecase.Submit.SubmitOutputData;

import java.awt.*;

public class SubmitPresenter implements SubmitOutputBoundary {

    private final SubmitViewModel submitViewModel;

    public SubmitPresenter(SubmitViewModel submitViewModel) {
        this.submitViewModel = submitViewModel;
    }

    @Override
    public void prepareSuccessView(SubmitOutputData submitOutputData) {
        SubmitState newSubmitState = new SubmitState();
        newSubmitState.setMessage(submitOutputData.getOutputMsg());
        newSubmitState.setMsgColor(Color.GREEN);
        submitViewModel.setState(newSubmitState);
        submitViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailureView(SubmitOutputData submitOutputData) {
        SubmitState newSubmitState = new SubmitState();
        String errorMsg = submitOutputData.getOutputMsg();
        newSubmitState.setMessage(errorMsg);
        newSubmitState.setMsgColor(Color.RED);
        submitViewModel.setState(newSubmitState);
        submitViewModel.firePropertyChange();
    }
}
