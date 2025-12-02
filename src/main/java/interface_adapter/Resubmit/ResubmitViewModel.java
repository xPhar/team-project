package interface_adapter.Resubmit;

import interface_adapter.ViewModel;

public class ResubmitViewModel extends ViewModel<ResubmitState> {

    public ResubmitViewModel() {
        super("resubmit");
        setState(new ResubmitState());
    }
}
