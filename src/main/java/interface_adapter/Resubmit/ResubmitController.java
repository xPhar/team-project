package interface_adapter.Resubmit;

import java.time.LocalDateTime;

import usecase.Resubmit.ResubmitInputBoundary;
import usecase.Resubmit.ResubmitInputData;

public class ResubmitController {

    private final ResubmitInputBoundary resubmitInteractor;

    /**
     * Constructs a ResubmitController with the specified ResubmitInputBoundary.
     *
     * @param resubmitInteractor The interactor that handles the resubmit use case
     */
    public ResubmitController(ResubmitInputBoundary resubmitInteractor) {
        this.resubmitInteractor = resubmitInteractor;
    }

    /**
     * Executes the resubmit operation by creating a new {@link ResubmitInputData}
     * object with the specified time and delegating the execution to the resubmit interactor.
     *
     * @param time The {@link LocalDateTime} representing the specific point in time for the resubmit action.
     */
    public void execute(LocalDateTime time) {
        final ResubmitInputData inputData = new ResubmitInputData(time);
        resubmitInteractor.execute(inputData);
    }
}
