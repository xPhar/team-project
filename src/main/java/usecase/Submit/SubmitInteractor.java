package usecase.Submit;

import entity.Assignment;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class SubmitInteractor implements SubmitInputBoundary {

    public static final String DDL_PASSED_MSG = "Deadline is passed, you cannot submit";
    public static final String WRONG_FILE_MSG = "File type not supported";
    public static final String SUCCESS_MSG = "Successfully submitted!";
    public static final String NETWORK_ERROR_MSG = "Network Error! Please try again later.";

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

        if (deadLinePassed(assignment)) return;
        if (wrongFileType(assignment, studentWork)) return;
        if (netWorkError(studentWork)) return;

        //go success
        submitPresenter.prepareSuccessView(new SubmitOutputData(SUCCESS_MSG));
    }


    private boolean netWorkError(File studentWork) {
        try {
            submitUserDataAccess.submit(studentWork);
        } catch (IOException e) {
            goFailure(NETWORK_ERROR_MSG);
            return true;
        }
        return false;
    }

    private boolean wrongFileType(Assignment assignment, File studentWork) {
        boolean matches = false;
        for (String suffix : assignment.getSupportedFileTypes()) {
            if (studentWork.getName().toLowerCase().endsWith("." + suffix.toLowerCase())) {
                matches = true;
                break;
            }
        }

        if (!matches) {
            goFailure(WRONG_FILE_MSG);
            return true;
        }
        return false;
    }

    private boolean deadLinePassed(Assignment assignment) {
        LocalDateTime deadline = assignment.getDueDate();
        LocalDateTime gracedDeadline = deadline.plusHours((int) assignment.getGracePeriod());
        if (gracedDeadline.isBefore(LocalDateTime.now())) {
            goFailure(DDL_PASSED_MSG);
            return true;
        }
        return false;
    }

    private void goFailure(String errMsg) {
        SubmitOutputData outputData = new SubmitOutputData(errMsg);
        submitPresenter.prepareFailureView(outputData);
    }
}
