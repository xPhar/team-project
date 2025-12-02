package interfaceadapter.Resubmit;

import interfaceadapter.ViewModel;

public class ResubmitViewModel extends ViewModel<ResubmitState> {

    public ResubmitViewModel() {
        super("resubmit");
        setState(new ResubmitState());
    }
}
