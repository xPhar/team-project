package data_access;

import entity.Assignment;
import entity.Submission;
import entity.User;

import usecase.resubmit.ResubmitUserDataAccessInterface;
import usecase.submit.SubmitUserDataAccessInterface;
import usecase.login.LoginDataAccessInterface;
import usecase.signup.SignupDataAccessInterface;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class FakeUserDataAccessObject implements SubmitUserDataAccessInterface, ResubmitUserDataAccessInterface,
                                                 LoginDataAccessInterface, SignupDataAccessInterface {
    private boolean submitFailure;
    private final Map<String, User> users;
    private final List<Assignment> assignments;
    Map<Assignment, Map<String, Submission>> submissionMap;
    private final SessionDataAccessObject session;

    // For Submit/Resubmit Testing
    public FakeUserDataAccessObject(boolean deadlinePassed, boolean submitFails) {
        this.users = new HashMap<>();
        this.assignments = new ArrayList<>();
        this.submissionMap = new HashMap<>();
        this.submitFailure = submitFails;
        this.session =  new SessionDataAccessObject();

        LocalDateTime deadline;

        if (deadlinePassed) {
            deadline = LocalDateTime.MIN;
        } else {
            deadline = LocalDateTime.MAX.minusHours(10000);
        }

        session.setAssignment(Assignment.builder()
                .name("dummyAssignment")
                .dueDate(deadline)
                .gracePeriod(1) // 1 hour
                .supportedFileTypes(List.of("txt", "java"))
                .build());
    }

    // For Login Testing
    public FakeUserDataAccessObject(LocalDateTime baseTime) {
        this.users = new HashMap<>();
        this.session = new  SessionDataAccessObject();
        users.put("FakeStudent1", new User("FakeStudent1", "password", "fake", "one", User.STUDENT));
        users.put("FakeStudent2", new User("FakeStudent2", "password", "fake", "two", User.STUDENT));
        users.put("FakeInstructor", new User("FakeInstructor", "password", "fake", "instructor", User.INSTRUCTOR));
        users.put("userThatThrowsException", null);
        this.assignments = new ArrayList<>();
        assignments.add(Assignment.builder().name("FakeAssignment1")
                        .description("FakeAssignment1 description")
                        .creationDate(baseTime)
                        .dueDate(baseTime.plusMinutes(30))
                        .gracePeriod(10)
                        .supportedFileTypes(List.of(".txt", ".java"))
                        .build());
        assignments.add(Assignment.builder().name("FakeAssignment2")
                        .description("FakeAssignment2 description")
                        .creationDate(baseTime.minusMinutes(240))
                        .dueDate(baseTime)
                        .gracePeriod(0)
                        .supportedFileTypes(List.of(".py"))
                        .build());
        this.submissionMap = new HashMap<>();
        submissionMap.put(assignments.get(0), Map.of(
                        "FakeStudent1", Submission.getBuilder().submitter("FakeStudent1")
                                .submissionTime(baseTime.minusMinutes(50))
                                .submissionName("pledge.txt")
                                .submissionData("This work is 100% my own (except the part my AI best friend did).")
                                .status(Submission.ON_TIME)
                                .build()));
        submissionMap.put(assignments.get(1), Map.of(
                        "FakeStudent1", Submission.getBuilder().submitter("FakeStudent1")
                                .submissionTime(baseTime.minusMinutes(50))
                                .submissionName("sample.py")
                                .submissionData("print(\"Hello, World!\")")
                                .status(Submission.GRADED)
                                .grade(21.0)
                                .feedback("very relevant feedback")
                                .build(),
                        "FakeStudent2", Submission.getBuilder().submitter("FakeStudent2")
                                .submissionTime(baseTime)
                                .submissionName("coolio.py")
                                .submissionData("phrase = \"Hello Python!\"\nprint(phrase)\n")
                                .status(Submission.LATE)
                                .build()
                ));
    }

    @Override
    public void submit(File studentFile) throws IOException {
        if (submitFailure) {
            throw new IOException("Assume there is network error");
        }
    }

    @Override
    public Assignment getAssignment() {
        return this.session.getAssignment();
    }

    @Override
    public boolean existsByName(String username) {
        return this.users.containsKey(username);
    }

    @Override
    public User getUser(String username) {
        User user = this.users.get(username);
        if (user == null) {
            throw new DataAccessException("User data is mangled. Please try a different account.");
        }
        return user;
    }

    @Override
    public void save(User user) {
        if (user != null) {
            users.put(user.getName(), user);
        }
    }

    @Override
    public void setActiveUser(User user) {
        save(user);
        session.setUser(user);
    }

    @Override
    public List<Assignment> getAssignments() {
        return this.assignments;
    }

    @Override
    public Submission getSubmission(Assignment assignment) {
        if (this.submissionMap.containsKey(assignment)) {
            Map<String, Submission> assignmentSubmissions = this.submissionMap.get(assignment);
            String username = session.getUser().getName();
            if (assignmentSubmissions.containsKey(username)) {
                return assignmentSubmissions.get(username);
            }
        }
        return null;
    }
}
