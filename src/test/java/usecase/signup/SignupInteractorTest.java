package usecase.signup;

import entity.User;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SignupInteractorTest {

    private static class InMemorySignupDAO implements SignupDataAccessInterface {
        private final Map<String, User> users = new HashMap<>();
        private final boolean throwOnSave;

        InMemorySignupDAO() {
            this(false);
        }

        InMemorySignupDAO(boolean throwOnSave) {
            this.throwOnSave = throwOnSave;
        }

        void seed(User user) {
            users.put(user.getName(), user);
        }

        @Override
        public boolean existsByName(String username) {
            return users.containsKey(username);
        }

        @Override
        public void save(User user) {
            if (throwOnSave) {
                throw new RuntimeException("save failed");
            }
            users.put(user.getName(), user);
        }

        @Override
        public User getUser(String username) {
            return users.get(username);
        }
    }

    private static class CapturingPresenter implements SignupOutputBoundary {
        SignupOutputData successData;
        String error;
        boolean switchedToLogin;

        @Override
        public void prepareSuccessView(SignupOutputData outputData) {
            this.successData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.error = errorMessage;
        }

        @Override
        public void switchToLoginView() {
            this.switchedToLogin = true;
        }
    }

    @Test
    void successfulStudentSignupSavesUserAndReturnsTrimmedUsername() {
        InMemorySignupDAO dao = new InMemorySignupDAO();
        CapturingPresenter presenter = new CapturingPresenter();
        SignupInteractor interactor = new SignupInteractor(dao, presenter);

        SignupInputData input = new SignupInputData(" newStudent ", "secret1", " Student ", "Jane Doe");
        interactor.execute(input);

        assertNotNull(presenter.successData, "Success view was not prepared.");
        assertNull(presenter.error, "Failure view should not be shown.");
        assertEquals("newStudent", presenter.successData.getUsername(), "Username should be trimmed in output.");

        User saved = dao.getUser("newStudent");
        assertNotNull(saved, "User was not saved.");
        assertEquals(User.STUDENT, saved.getUserType(), "User type should be student.");
        assertEquals("Jane", saved.getFirstName());
        assertEquals("Doe", saved.getLastName().trim());
    }

    @Test
    void successfulInstructorSignupStoresRole() {
        InMemorySignupDAO dao = new InMemorySignupDAO();
        CapturingPresenter presenter = new CapturingPresenter();
        SignupInteractor interactor = new SignupInteractor(dao, presenter);

        SignupInputData input = new SignupInputData("professor1", "longpass", "Instructor", "Alan Turing");
        interactor.execute(input);

        assertNotNull(presenter.successData, "Success view was not prepared.");
        User saved = dao.getUser("professor1");
        assertNotNull(saved, "User was not saved.");
        assertEquals(User.INSTRUCTOR, saved.getUserType(), "User type should be instructor.");
    }

    @Test
    void duplicateUsernameShowsErrorAndDoesNotSave() {
        InMemorySignupDAO dao = new InMemorySignupDAO();
        dao.seed(new User("existingUser", "pwd", "First", "Last", User.STUDENT));
        CapturingPresenter presenter = new CapturingPresenter();
        SignupInteractor interactor = new SignupInteractor(dao, presenter);

        interactor.execute(new SignupInputData("existingUser", "password", "Student", "Jane Doe"));

        assertNull(presenter.successData, "Success view should not be prepared.");
        assertEquals("Username 'existingUser' already exists. Please choose a different username.", presenter.error);
        assertEquals("pwd", dao.getUser("existingUser").getPassword(), "Existing user should remain unchanged.");
    }

    @Test
    void invalidRoleIsRejected() {
        InMemorySignupDAO dao = new InMemorySignupDAO();
        CapturingPresenter presenter = new CapturingPresenter();
        SignupInteractor interactor = new SignupInteractor(dao, presenter);

        interactor.execute(new SignupInputData("newUser", "password", "TA", "Jane Doe"));

        assertNull(presenter.successData);
        assertEquals("Invalid user role. Must be 'Student' or 'Instructor'.", presenter.error);
        assertNull(dao.getUser("newUser"), "User should not be saved when role is invalid.");
    }

    @Test
    void missingFullNameIsRejected() {
        InMemorySignupDAO dao = new InMemorySignupDAO();
        CapturingPresenter presenter = new CapturingPresenter();
        SignupInteractor interactor = new SignupInteractor(dao, presenter);

        interactor.execute(new SignupInputData("newUser", "password", "Student", "   "));

        assertNull(presenter.successData);
        assertEquals("Full name cannot be empty.", presenter.error);
        assertNull(dao.getUser("newUser"));
    }

    @Test
    void shortPasswordIsRejected() {
        InMemorySignupDAO dao = new InMemorySignupDAO();
        CapturingPresenter presenter = new CapturingPresenter();
        SignupInteractor interactor = new SignupInteractor(dao, presenter);

        interactor.execute(new SignupInputData("newUser", "short", "Student", "Jane Doe"));

        assertNull(presenter.successData);
        assertEquals("Password must be at least 6 characters long.", presenter.error);
    }

    @Test
    void dataAccessFailureIsSurfacedAsRegistrationFailure() {
        InMemorySignupDAO dao = new InMemorySignupDAO(true);
        CapturingPresenter presenter = new CapturingPresenter();
        SignupInteractor interactor = new SignupInteractor(dao, presenter);

        interactor.execute(new SignupInputData("newUser", "password", "Student", "Jane Doe"));

        assertNull(presenter.successData);
        assertEquals("Registration failed: save failed", presenter.error);
    }
}
