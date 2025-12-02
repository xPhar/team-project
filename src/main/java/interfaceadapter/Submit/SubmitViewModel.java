package interfaceadapter.Submit;

import interfaceadapter.ViewModel;

public class SubmitViewModel extends ViewModel<SubmitState> {

    public SubmitViewModel() {
        super("submit");
        setState(new SubmitState());
    }
}
