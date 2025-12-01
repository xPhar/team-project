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
    public void execute(ResubmitInputData inputData) {
        LocalDateTime deadline = resubmitUserDataAccess.getAssignment().getDueDate();
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

    public void switchToSubmitView() {
        resubmitOutputBoundary.prepareSuccessView();
    }
}
