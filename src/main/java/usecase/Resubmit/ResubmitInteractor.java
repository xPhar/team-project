package usecase.Resubmit;

import app.Session;

import java.time.LocalDateTime;

public class ResubmitInteractor implements ResubmitInputBoundary {

    private final ResubmitOutputBoundary resubmitOutputBoundary;
    private final Session session;

    public ResubmitInteractor(ResubmitOutputBoundary resubmitOutputBoundary,
                              Session session) {
        this.resubmitOutputBoundary = resubmitOutputBoundary;
        this.session = session;
    }
    public void execute(ResubmitInputData inputData) {
        LocalDateTime deadline = session.getAssignment().getDueDate();
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
