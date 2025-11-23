package usecase.Submit;

import entity.Assignment;

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

        Assignment assignment = submitUserDataAccess.getAssignment();
        File studentWork = inputData.getSelectedFile();

        // check for deadline
        LocalDateTime deadline = assignment.getDueDate();
        LocalDateTime gracedDeadline = deadline.plusHours((int) assignment.getGracePeriod());
        if (gracedDeadline.isBefore(LocalDateTime.now())) {
            SubmitOutputData outputData = new SubmitOutputData("Deadline is passed, you cannot submit");
            submitPresenter.prepareFailureView(outputData);
            return;
        }

        // check for supported file type
        boolean matches = false;
        for (String suffix : assignment.getSupportedFileTypes()) {
            if (studentWork.getName().toLowerCase().endsWith("." + suffix.toLowerCase())) {
                matches = true;
                break;
            }
        }

        if (!matches) {
            SubmitOutputData outputData = new SubmitOutputData("File type not supported");
            submitPresenter.prepareFailureView(outputData);
        }


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
