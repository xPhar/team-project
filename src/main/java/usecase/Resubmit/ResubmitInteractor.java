package usecase.Resubmit;

import java.time.LocalDateTime;

public class ResubmitInteractor implements ResubmitInputBoundary {

    public static final String ERROR_MESSAGE = "DDL is passed, you cannot resubmit your assignment!";
    private final ResubmitUserDataAccessInterface resubmitUserDataAccess;
    private final ResubmitOutputBoundary resubmitOutputBoundary;

    public ResubmitInteractor(ResubmitOutputBoundary resubmitOutputBoundary,
                              ResubmitUserDataAccessInterface resubmitUserDataAccess) {
        this.resubmitOutputBoundary = resubmitOutputBoundary;
        this.resubmitUserDataAccess = resubmitUserDataAccess;
    }

    /**
     * Executes the resubmit use case by checking if the provided resubmission time
     * is before the assignment deadline. If the resubmission is within the allowed
     * time frame, the view is switched to the submission success view. Otherwise,
     * a failure message is prepared.
     *
     * @param inputData the input data containing the timestamp of the resubmission
     */
    public void execute(ResubmitInputData inputData) {
        final LocalDateTime deadline = resubmitUserDataAccess.getAssignment().getDueDate();
        // If we want to add disability part, we can adjust color of message
        //  but if we do not need that, then we can delete ResubmitOutputData
        if (deadline.isAfter(inputData.getTime())) {
            switchToSubmitView();
        }
        else {
            resubmitOutputBoundary.prepareFailView(ERROR_MESSAGE);
        }
    }

    @Override
    public void goBack() {
        resubmitOutputBoundary.switchToLogginView();
    }


    /**
     * Switches the user interface to the submission success view.
     * This method is invoked when the resubmission is confirmed to be within the allowed time frame.
     * It delegates the preparation of the success view to the output boundary.
     */
    public void switchToSubmitView() {
        resubmitOutputBoundary.prepareSuccessView();
    }
}
