package usecase.Resubmit;

import entity.Session;

import java.time.LocalDateTime;

public class ResubmitInteractor implements ResubmitInputBoundary {

    private final ResubmitOutputBoundary resubmitOutputBoundary;

    public ResubmitInteractor(ResubmitOutputBoundary resubmitOutputBoundary) {
        this.resubmitOutputBoundary = resubmitOutputBoundary;
    }
    public void execute(ResubmitInputData inputData) {
        LocalDateTime deadline = resubmitUserDataAccess.getAssignmentDueDate();
        // TODO: if we need to add disability part, we can adjust color of message
        //  but if we do not need that, then we can delete ResubmitOutputData
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
