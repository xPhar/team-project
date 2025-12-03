package interface_adapter.submit;

import interface_adapter.ViewModel;

public class SubmitViewModel extends ViewModel<SubmitState> {

    public SubmitViewModel() {
        super("submit");
        setState(new SubmitState());
    }
}
