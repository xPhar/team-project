package interfaceadapter.Submit;

import java.io.File;
import java.time.LocalDateTime;

import usecase.Submit.SubmitInputBoundary;
import usecase.Submit.SubmitInputData;

public class SubmitController {

    private final SubmitInputBoundary submitUsecaseInteractor;

    public SubmitController(SubmitInputBoundary submitInputBoundary) {
        this.submitUsecaseInteractor = submitInputBoundary;
    }

    /**
     * Executes the submit operation by wrapping the provided time and file into an input data object
     * and passing it to the use case interactor.
     *
     * @param time        The timestamp at which the submit operation is triggered.
     * @param selectedFile The file chosen for submission.
     */
    public void submitExecute(LocalDateTime time, File selectedFile) {
        final SubmitInputData inputData = new SubmitInputData(time, selectedFile);
        submitUsecaseInteractor.execute(inputData);
    }

    public void backExecute() {
        submitUsecaseInteractor.backToLoggedInView();
    }
}
