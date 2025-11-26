package usecase.class_average;

/**
 * The output boundary for the Class Average Use Case.
 */
public interface ClassAverageOutputBoundary {
    /**
     * Prepares the success view for the Class Average Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(ClassAverageOutputData outputData);

    /**
     * Returns back to loggedIn view
     */
    void backToLoggedInView();

    /**
     * Prepares the failure view for the Class Average Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
