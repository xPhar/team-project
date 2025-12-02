package interfaceadapter.EditAssignment;

import java.util.ArrayList;

import interfaceadapter.ViewModel;

public class EditAssignmentViewModel extends ViewModel<EditAssignmentState> {
    public static final String TITLE_LABEL = "Edit Assignment";

    public EditAssignmentViewModel() {
        super("Edit Assignment");
        setState(new EditAssignmentState("", "", "", null, 0.0, new ArrayList<>()));
    }
}
