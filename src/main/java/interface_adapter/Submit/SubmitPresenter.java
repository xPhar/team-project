package interface_adapter.Submit;

import java.awt.Color;

import usecase.Submit.SubmitOutputBoundary;
import usecase.Submit.SubmitOutputData;

public class SubmitPresenter implements SubmitOutputBoundary {

    private final SubmitViewModel submitViewModel;

    public SubmitPresenter(SubmitViewModel submitViewModel) {
        this.submitViewModel = submitViewModel;
    }

    @Override
    public void prepareSuccessView(SubmitOutputData submitOutputData) {
        final SubmitState newSubmitState = new SubmitState();
        newSubmitState.setMessage(submitOutputData.getOutputMsg());
        newSubmitState.setMsgColor(Color.GREEN);
        submitViewModel.setState(newSubmitState);
        submitViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailureView(SubmitOutputData submitOutputData) {
        final SubmitState newSubmitState = new SubmitState();
        final String errorMsg = submitOutputData.getOutputMsg();
        newSubmitState.setMessage(errorMsg);
        newSubmitState.setMsgColor(Color.RED);
        submitViewModel.setState(newSubmitState);
        submitViewModel.firePropertyChange();
    }
}
