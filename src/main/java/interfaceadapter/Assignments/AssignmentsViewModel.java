package interfaceadapter.Assignments;

import interfaceadapter.ViewModel;

public class AssignmentsViewModel extends ViewModel<AssignmentsState> {
    public static final String TITLE_LABEL = "Assignments";

    public AssignmentsViewModel() {
        super("Assignments");
        setState(new AssignmentsState());
    }
}
