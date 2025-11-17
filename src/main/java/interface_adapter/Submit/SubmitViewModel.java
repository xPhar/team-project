package interface_adapter.Submit;

import interface_adapter.ViewModel;

public class SubmitViewModel extends ViewModel<SubmitState> {

    public SubmitViewModel() {
        super("submit");
        setState(new SubmitState());
    }
}