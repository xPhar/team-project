package data_access;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import entity.Assignment;
import entity.Submission;
import entity.User;
import usecase.Resubmit.ResubmitUserDataAccessInterface;
import usecase.Submit.SubmitUserDataAccessInterface;
import usecase.login.LoginDataAccessInterface;

public class FakeUserDataAccessObject implements SubmitUserDataAccessInterface, ResubmitUserDataAccessInterface,
        LoginDataAccessInterface {
    public static final int HOUR_SHIFTING = 10000;
    private final Assignment assignment;
    private final boolean submitFailure;

    public FakeUserDataAccessObject(boolean deadlinePassed, boolean submitFails) {
        this.submitFailure = submitFails;

        final LocalDateTime deadline;

        if (deadlinePassed) {
            deadline = LocalDateTime.MIN;
        }
        else {
            deadline = LocalDateTime.MAX.minusHours(HOUR_SHIFTING);
        }

        assignment = Assignment.builder()
                .name("dummyAssignment")
                .dueDate(deadline)
                .gracePeriod(1)
                .supportedFileTypes(List.of("txt", "java"))
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

    @Override
    public boolean existsByName(String username) {
        return false;
    }

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public void setActiveUser(User user) {
        // Does nothing :D
    }

    @Override
    public List<Assignment> getAssignments() {
        return List.of();
    }

    @Override
    public Submission getSubmission(Assignment assignment) {
        return null;
    }
}
