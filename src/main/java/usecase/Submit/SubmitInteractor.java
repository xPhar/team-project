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

        File studentWork = inputData.getSelectedFile();

        try {
            submitUserDataAccess.submit(studentWork);

            // This part might be useless because we do not need submission for student, we may only
            // use it for instructor fetch data from dataAccessObject
            //Student student = (Student) session.getUser();
            //Submission thisSubmission = new Submission();
            // TODO: add submission data, wait for submission implementation
            //student.addSubmission(thisSubmission);

            SubmitOutputData outputData = new SubmitOutputData("Successfully submitted!");
            submitPresenter.prepareSuccessView(outputData);
        } catch (IOException e) {
            SubmitOutputData outputData = new SubmitOutputData("Network Error! Please try again later.");
            submitPresenter.prepareFailureView(outputData);
        }
    }
}
