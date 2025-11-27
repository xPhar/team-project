package usecase.class_average;

/**
 * Input Data for the Class Average Use Case.
 * It carries the assignment name selected by the user
 * from the Controller into the Interactor.
 */
public class ClassAverageInputData {

    private final String assignmentName;
    private final boolean back;

    public ClassAverageInputData(boolean back, String assignmentName) {
        this.back = back;
        this.assignmentName = assignmentName;
    }

    public boolean getBack() {
        return back;
    }

    public String getAssignmentName() {
        return assignmentName;
    }
}
