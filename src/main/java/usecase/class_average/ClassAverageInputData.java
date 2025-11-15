package usecase.class_average;

/**
 * Input Data for the Class Average Use Case.
 * It carries the assignment name selected by the user
 * from the Controller into the Interactor.
 */
public class ClassAverageInputData {

    private final String assignmentName;

    public ClassAverageInputData(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getAssignmentName() {
        return assignmentName;
    }
}
