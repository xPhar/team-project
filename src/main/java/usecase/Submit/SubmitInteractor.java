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

        final Assignment assignment = submitUserDataAccess.getAssignment();
        final File studentWork = inputData.getSelectedFile();
        final LocalDateTime curTime = inputData.getTime();

        if (deadLinePassed(assignment, curTime)) return;
        if (wrongFileType(assignment, studentWork)) return;
        if (netWorkError(studentWork)) return;

        //go success
        submitPresenter.prepareSuccessView(new SubmitOutputData(SUCCESS_MSG));
    }

    @Override
    public void backToLoggedInView() {
        submitPresenter.switchToLoggedInView();
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
        for (String suffix : assignment.getSupportedFileTypes()) {
            if (studentWork.getName().toLowerCase().endsWith("." + suffix.toLowerCase())) {
                return false;
            }
        }
        // Case no matching suffix
        goFailure(WRONG_FILE_MSG);
        return true;
    }

    private boolean deadLinePassed(Assignment assignment, LocalDateTime curTime) {
        LocalDateTime deadline = assignment.getDueDate();
        LocalDateTime gracedDeadline = deadline.plusHours((int) assignment.getGracePeriod());
        if (curTime.isAfter(gracedDeadline)) {
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
