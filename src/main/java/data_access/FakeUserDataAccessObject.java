package data_access;

import entity.Assignment;
import usecase.Resubmit.ResubmitUserDataAccessInterface;
import usecase.Submit.SubmitUserDataAccessInterface;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class FakeUserDataAccessObject implements ResubmitUserDataAccessInterface, SubmitUserDataAccessInterface {
    Assignment assignment;
    boolean submitFailure;


    public FakeUserDataAccessObject(boolean deadlinePassed, boolean submitFails) {
        this.submitFailure = submitFails;

        LocalDateTime deadline;

        if (deadlinePassed) {
            deadline = LocalDateTime.MIN;
        }
        else {
            deadline = LocalDateTime.MAX.minusHours(10000);
        }

        assignment = Assignment.builder()
                .name("CRYCHIC を止める")
                .dueDate(deadline)
                .gracePeriod(1) // 1 hour
                .supportedFileTypes(List.of("txt","java"))
                .build();
    }

    @Override
    public void submit(File studentFile) throws IOException {
        if (submitFailure) {
            throw new IOException("Assume there is network error");
        }
    }

    @Override
    public Assignment getAssignment() {
        return this.assignment;
    }
}
