package usecase.Submit;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class SubmitInteractor implements SubmitInputBoundary {

    private final SubmitUserDataAccessInterface submitUserDataAccess;
    private final SubmitOutputBoundary submitPresenter;

    public SubmitInteractor(SubmitUserDataAccessInterface submitUserDataAccess,
                            SubmitOutputBoundary submitOutputBoundary) {
        this.submitUserDataAccess = submitUserDataAccess;
        this.submitPresenter = submitOutputBoundary;
    }

    @Override
    public void execute(SubmitInputData inputData) {
        LocalDateTime deadline = submitUserDataAccess.getAssignmentDueDate();
        if (deadline.isBefore(LocalDateTime.now())) {
            SubmitOutputData outputData = new SubmitOutputData("Deadline is passed, you cannot submit");
            submitPresenter.prepareFailureView(outputData);

        }
        else {

            File studentWork = inputData.getSelectedFile();

            try {
                submitUserDataAccess.submit(studentWork);

                SubmitOutputData outputData = new SubmitOutputData("Successfully submitted!");
                submitPresenter.prepareSuccessView(outputData);

            } catch (IOException e) { //Case network error
                SubmitOutputData outputData = new SubmitOutputData("Network Error! Please try again later.");
                submitPresenter.prepareFailureView(outputData);
            }
        }
    }
}
