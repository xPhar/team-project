package usecase.Submit;

import entity.Session;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class SubmitInteractor implements SubmitInputBoundary {

    private final SubmitUserDataAccessInterface submitUserDataAccess;
    private final SubmitOutputBoundary submitPresenter;
    private final Session session;

    public SubmitInteractor(SubmitUserDataAccessInterface submitUserDataAccess,
                            SubmitOutputBoundary submitOutputBoundary) {
        this.submitUserDataAccess = submitUserDataAccess;
        this.submitPresenter = submitOutputBoundary;
        this.session = Session.getInstance();
    }

    @Override
    public void execute(SubmitInputData inputData) {

        LocalDateTime deadline = session.getAssignment().getDueDate();
        if (deadline.isBefore(inputData.getTime())) { // case DDL passed
            SubmitOutputData outputData = new SubmitOutputData("Deadline is passed, you cannot submit");
            submitPresenter.prepareFailureView(outputData);

        }else {

            File studentWork = inputData.getSelectedFile();

            try { //case expected
                submitUserDataAccess.submit(studentWork,
                        session.getUser(),
                        session.getCourse(),
                        session.getAssignment()
                );

                SubmitOutputData outputData = new SubmitOutputData("Successfully submitted!");
                submitPresenter.prepareSuccessView(outputData);

            } catch (IOException e) { //Case network error
                SubmitOutputData outputData = new SubmitOutputData("Network Error! Please try again later.");
                submitPresenter.prepareFailureView(outputData);
            }
        }
    }
}
