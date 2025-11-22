package usecase.Submit;

import app.Session;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class SubmitInteractor implements SubmitInputBoundary {

    private final SubmitUserDataAccessInterface submitUserDataAccess;
    private final SubmitOutputBoundary submitPresenter;
    private final Session session;

    public SubmitInteractor(SubmitUserDataAccessInterface submitUserDataAccess,
                            SubmitOutputBoundary submitOutputBoundary,
                            Session session) {
        this.submitUserDataAccess = submitUserDataAccess;
        this.submitPresenter = submitOutputBoundary;
        this.session = session;
    }

    @Override
    public void execute(SubmitInputData inputData) {

        LocalDateTime deadline = session.getAssignment().getDueDate();
        if (deadline.isBefore(LocalDateTime.now())) {
            SubmitOutputData outputData = new SubmitOutputData("Deadline is passed, you cannot submit");
            submitPresenter.prepareFailureView(outputData);
        }

        File studentWork = inputData.getSelectedFile();

        try {
            submitUserDataAccess.submit(studentWork,
                    session.getUser().getName(),
                    session.getAssignment().getName(),
                    session.getCourse().getCourseCode());

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
