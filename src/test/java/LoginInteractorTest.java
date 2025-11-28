package usecase.login;

import entity.Assignment;
import entity.Submission;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LoginInteractorTest {

    private RecordingLoginPresenter presenter;
    private InMemoryLoginDao dao;
    private LoginInteractor interactor;

    @BeforeEach
    void setUp() {
        presenter = new RecordingLoginPresenter();
        dao = new InMemoryLoginDao();
        interactor = new LoginInteractor(dao, presenter);
    }

    @Test
    void studentLoginSuccessBuildsAssignmentTableAndSetsCurrentUser() {
        Assignment assignment = Assignment.builder()
                .name("A1")
                .dueDate(LocalDateTime.now().plusDays(1))
                .gracePeriod(1.0)
                .build();
        Submission submission = new Submission("A1", "student", LocalDateTime.now(),
                "file.txt", "data", 95.0, Submission.Status.GRADED, "Great");

        dao.storeUser(new User("student", "pass", User.STUDENT));
        dao.setAssignments(List.of(assignment));
        dao.addSubmission(assignment.getName(), submission);

        interactor.execute(new LoginInputData("student", "pass"));

        assertNull(presenter.failMessage);
        assertNotNull(presenter.successData);
        assertEquals("student", presenter.successData.getUsername());
        assertEquals("student", presenter.successData.getUserRole());
        assertEquals("student", dao.currentUsername);
        assertNotNull(presenter.successData.getAssignments());
        assertEquals(1, presenter.successData.getAssignments().length);
        assertEquals("A1", presenter.successData.getAssignments()[0][0]);
        assertEquals("✔", presenter.successData.getAssignments()[0][3]);
    }

    @Test
    void instructorLoginSuccessDoesNotNeedAssignments() {
        dao.storeUser(new User("prof", "secret", User.INSTRUCTOR));

        interactor.execute(new LoginInputData("prof", "secret"));

        assertNull(presenter.failMessage);
        assertNotNull(presenter.successData);
        assertEquals("prof", presenter.successData.getUsername());
        assertEquals("instructor", presenter.successData.getUserRole());
        assertNull(presenter.successData.getAssignments());
    }

    @Test
    void loginFailsWhenUserMissing() {
        interactor.execute(new LoginInputData("missing", "x"));
        assertEquals("Account 'missing' does not exist.", presenter.failMessage);
        assertNull(presenter.successData);
    }

    @Test
    void loginFailsWhenPasswordWrong() {
        dao.storeUser(new User("user", "right", User.STUDENT));
        interactor.execute(new LoginInputData("user", "wrong"));
        assertEquals("Incorrect password for \"user\".", presenter.failMessage);
    }

    @Test
    void loginFailsWhenUsernameEmpty() {
        interactor.execute(new LoginInputData("", "pass"));
        assertEquals("Username cannot be empty.", presenter.failMessage);
    }

    @Test
    void loginFailsWhenPasswordEmpty() {
        interactor.execute(new LoginInputData("user", ""));
        assertEquals("Password cannot be empty.", presenter.failMessage);
    }

    @Test
    void switchToSignupDelegates() {
        interactor.switchToSignupView();
        assertTrue(presenter.switchedToSignup);
    }

    private static class RecordingLoginPresenter implements LoginOutputBoundary {
        LoginOutputData successData;
        String failMessage;
        boolean switchedToSignup;

        @Override
        public void prepareSuccessView(LoginOutputData outputData) {
            this.successData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.failMessage = errorMessage;
        }

        @Override
        public void switchToSignupView() {
            this.switchedToSignup = true;
        }
    }

    private static class InMemoryLoginDao implements LoginDataAccessInterface {
        private final Map<String, User> users = new HashMap<>();
        private final Map<String, Submission> submissions = new HashMap<>();
        private List<Assignment> assignments = new ArrayList<>();
        private User activeUser;
        private String currentUsername = "";

        void storeUser(User user) {
            users.put(user.getName(), user);
        }

        void setAssignments(List<Assignment> assignments) {
            this.assignments = assignments;
        }

        void addSubmission(String assignmentName, Submission submission) {
            submissions.put(assignmentName, submission);
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
        public void setActiveUser(User user) {
            this.activeUser = user;
        }

        @Override
        public List<Assignment> getAssignments() {
            return assignments;
        }

        @Override
        public Submission getSubmission(Assignment assignment) {
            return submissions.get(assignment.getName());
        }

        @Override
        public void setCurrentUsername(String name) {
            this.currentUsername = name;
        }

        @Override
        public String getCurrentUsername() {
            return currentUsername;
        }
    }
}
