package usecase.Resubmit;

import java.time.LocalDateTime;

public class ResubmitInteractor implements ResubmitInputBoundary {

    private final ResubmitOutputBoundary resubmitOutputBoundary;

    public ResubmitInteractor(ResubmitOutputBoundary resubmitOutputBoundary) {
        this.resubmitOutputBoundary = resubmitOutputBoundary;
    }
    public void execute(ResubmitInputData inputData) {
        LocalDateTime deadline = resubmitUserDataAccess.getAssignmentDueDate();
        if (deadline.isAfter(inputData.getTime())) {
            switchToSubmitView();
        }else{
            resubmitOutputBoundary.prepareFailView("DDL is passed, you cannot resubmit your assignment!");
        }
    }
    public void switchToSubmitView() {
        resubmitOutputBoundary.prepareSuccessView();
    }
}
