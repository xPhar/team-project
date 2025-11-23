package data_access;

import usecase.Resubmit.ResubmitUserDataAccessInterface;
import usecase.Submit.SubmitUserDataAccessInterface;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class FakeUserDataAccessObject implements ResubmitUserDataAccessInterface, SubmitUserDataAccessInterface {
    LocalDateTime deadline;
    boolean submitFailure;

    public FakeUserDataAccessObject(boolean deadlinePassed, boolean submitFails) {
        this.submitFailure = submitFails;

        if (deadlinePassed) {
            this.deadline = LocalDateTime.MIN;
        }
        else {
            this.deadline = LocalDateTime.MAX;
        }
    }

    @Override
    public void submit(File studentFile) throws IOException {
        if (submitFailure) {
            throw new IOException("Assume there is network error");
        }
    }

    @Override
    public LocalDateTime getAssignmentDueDate() {
        return this.deadline;
    }
}
