package data_access;

import entity.Assignment;
import entity.Submission;
import entity.User;

import usecase.Resubmit.ResubmitUserDataAccessInterface;
import usecase.Submit.SubmitUserDataAccessInterface;
import usecase.login.LoginDataAccessInterface;
import usecase.signup.SignupDataAccessInterface;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeUserDataAccessObject implements SubmitUserDataAccessInterface, ResubmitUserDataAccessInterface,
                                                 LoginDataAccessInterface, SignupDataAccessInterface {
    Assignment assignment;
    boolean submitFailure;
    private final Map<String, User> users = new HashMap<>();
    private String currentUsername = "";


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
        return users.containsKey(username);
    }

    @Override
    public User getUser(String username) {
        return users.get(username);
    }

    @Override
    public void save(User user) {
        if (user != null) {
            users.put(user.getName(), user);
        }
    }

    @Override
    public User get(String username) {
        return getUser(username);
    }

    @Override
    public void setCurrentUsername(String name) {
        this.currentUsername = name;
    }

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public void setActiveUser(User user) {
        if (user != null) {
            save(user);
            setCurrentUsername(user.getName());
        }
    }

    @Override
    public List<Assignment> getAssignments() {
        return List.of(assignment);
    }

    @Override
    public Submission getSubmission(Assignment assignment) {
        return null;
    }
}
