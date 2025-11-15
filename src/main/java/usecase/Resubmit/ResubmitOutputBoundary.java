package usecase.Resubmit;

public interface ResubmitOutputBoundary {
    /**
     * Prepares the success view for the Resubmit Use Case.
     */

    void prepareSuccessView();

    /**
     * Prepares the failure view for the Resubmit Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches to the Submit View.
     */
    void switchToSubmitView();

}

