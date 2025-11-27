package data_access;

import entity.Assignment;
import entity.Submission;
import entity.User;

import usecase.Resubmit.ResubmitUserDataAccessInterface;
import usecase.Submit.SubmitUserDataAccessInterface;
import usecase.login.LoginDataAccessInterface;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class FakeUserDataAccessObject implements SubmitUserDataAccessInterface, ResubmitUserDataAccessInterface,
                                                 LoginDataAccessInterface {
    Assignment assignment;
    boolean submitFailure;


    public FakeUserDataAccessObject(boolean deadlinePassed, boolean submitFails) {
        this.submitFailure = submitFails;

        LocalDateTime deadline;

        if (deadlinePassed) {
            deadline = LocalDateTime.MIN;
        } else {
            deadline = LocalDateTime.MAX.minusHours(10000);
        }

        assignment = Assignment.builder()
                .name("dummyAssignment")
                .dueDate(deadline)
                .gracePeriod(1) // 1 hour
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
