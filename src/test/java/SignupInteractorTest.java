package usecase.signup;

import entity.Instructor;
import entity.Student;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SignupInteractorTest {

    private RecordingSignupPresenter presenter;
    private InMemorySignupDao dao;
    private SignupInteractor interactor;

    @BeforeEach
    void setUp() {
        presenter = new RecordingSignupPresenter();
        dao = new InMemorySignupDao();
        interactor = new SignupInteractor(dao, presenter);
    }

    @Test
    void registerStudentSuccess() {
        SignupInputData input = new SignupInputData("newstudent", "password123", "Student", "John Doe");
        interactor.execute(input);

        assertNotNull(presenter.successData);
        assertEquals("newstudent", presenter.successData.getUsername());
        assertTrue(dao.existsByName("newstudent"));
        User saved = dao.get("newstudent");
        assertInstanceOf(Student.class, saved);
        assertEquals("newstudent", dao.getCurrentUsername());
    }

    @Test
    void registerInstructorSuccess() {
        SignupInputData input = new SignupInputData("prof", "password123", "Instructor", "Prof X");
        interactor.execute(input);

        assertTrue(dao.existsByName("prof"));
        assertInstanceOf(Instructor.class, dao.get("prof"));
        assertEquals("prof", dao.getCurrentUsername());
    }

    @Test
    void duplicateUsernameFails() {
        dao.save(new Student("existing", "pass"));
        interactor.execute(new SignupInputData("existing", "password123", "Student", "Jane Doe"));
        assertEquals("Username 'existing' already exists. Please choose a different username.", presenter.failMessage);
    }

    @Test
    void validationFailures() {
        interactor.execute(new SignupInputData("", "password123", "Student", "Name"));
        assertEquals("Username cannot be empty.", presenter.failMessage);

        interactor.execute(new SignupInputData("ab", "password123", "Student", "Name"));
        assertEquals("Username must be at least 3 characters long.", presenter.failMessage);

        interactor.execute(new SignupInputData("user", "123", "Student", "Name"));
        assertEquals("Password must be at least 6 characters long.", presenter.failMessage);

        interactor.execute(new SignupInputData("user", "password123", "Student", ""));
        assertEquals("Full name cannot be empty.", presenter.failMessage);

        interactor.execute(new SignupInputData("user", "password123", "Invalid", "Name"));
        assertEquals("Invalid user role. Must be 'Student' or 'Instructor'.", presenter.failMessage);
    }

    @Test
    void switchToLoginDelegates() {
        interactor.switchToLoginView();
        assertTrue(presenter.switchedToLogin);
    }

    private static class RecordingSignupPresenter implements SignupOutputBoundary {
        SignupOutputData successData;
        String failMessage;
        boolean switchedToLogin;

        @Override
        public void prepareSuccessView(SignupOutputData outputData) {
            this.successData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.failMessage = errorMessage;
        }

        @Override
        public void switchToLoginView() {
            this.switchedToLogin = true;
        }
    }

    private static class InMemorySignupDao implements SignupDataAccessInterface {
        private final Map<String, User> users = new HashMap<>();
        private String currentUsername = "";

        @Override
        public boolean existsByName(String username) {
            return users.containsKey(username);
        }

        @Override
        public void save(User user) {
            users.put(user.getName(), user);
        }

        @Override
        public User getUser(String username) {
            return users.get(username);
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
