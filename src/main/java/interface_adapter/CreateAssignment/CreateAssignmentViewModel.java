package interface_adapter.CreateAssignment;

import interface_adapter.ViewModel;

public class CreateAssignmentViewModel extends ViewModel<CreateAssignmentState> {
    public static final String TITLE_LABEL = "Create Assignment";

    public CreateAssignmentViewModel() {
        super("Create Assignment");
        setState(new CreateAssignmentState());
    }
}
