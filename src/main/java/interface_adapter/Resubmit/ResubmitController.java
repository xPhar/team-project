package interface_adapter.Resubmit;

import usecase.Resubmit.ResubmitInputBoundary;
import usecase.Resubmit.ResubmitInputData;

import java.time.LocalDateTime;

public class ResubmitController {

    private final ResubmitInputBoundary resubmitInteractor;
    public ResubmitController(ResubmitInputBoundary resubmitInteractor){
        this.resubmitInteractor = resubmitInteractor;
    }
    public void execute(LocalDateTime time){
        ResubmitInputData inputData = new ResubmitInputData(time);
        resubmitInteractor.execute(inputData);
    }
}
