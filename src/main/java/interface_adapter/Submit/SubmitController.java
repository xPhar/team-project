package interface_adapter.Submit;

import usecase.Submit.SubmitInputBoundary;
import usecase.Submit.SubmitInputData;

import java.io.File;
import java.time.LocalDateTime;


public class SubmitController {

    private final SubmitInputBoundary submitUsecaseInteractor;

    public SubmitController(SubmitInputBoundary submitInputBoundary){
        this.submitUsecaseInteractor = submitInputBoundary;
    }

    public void submitExecute(LocalDateTime time, File selectedFile){
        final SubmitInputData inputData = new SubmitInputData(time, selectedFile);
        submitUsecaseInteractor.execute(inputData);
    }
}
