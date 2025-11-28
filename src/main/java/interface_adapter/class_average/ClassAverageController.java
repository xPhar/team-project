package interface_adapter.class_average;

import usecase.class_average.ClassAverageInputBoundary;
import usecase.class_average.ClassAverageInputData;

/**
 * Controller for the class average use case.
 * Called by the ClassAverageView when the user selects an assignment.
 */
public class ClassAverageController {

    private final ClassAverageInputBoundary classAverageUseCaseInteractor;

    public ClassAverageController(ClassAverageInputBoundary classAverageUseCaseInteractor) {
        this.classAverageUseCaseInteractor = classAverageUseCaseInteractor;
    }

    /**
     * Execute the class average use case for the selected assignment.
     *
     * @param back whether the user requested to go back to the previous view
     * @param assignmentName the name of the assignment chosen in the ComboBox
     */
    public void execute(boolean back, String assignmentName) {
        final ClassAverageInputData inputData = new ClassAverageInputData(back, assignmentName);
        classAverageUseCaseInteractor.execute(inputData);
    }
}
