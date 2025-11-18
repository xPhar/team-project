package usecase.signup;

import entity.Student;
import entity.Instructor;
import entity.User;
import data_access.DummyDataAccessObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SignupInteractorTest {
    private SignupInteractor signupInteractor;
    private DummyDataAccessObject userRepository;
    private SignupOutputBoundary mockPresenter;

    @BeforeEach
    void setUp() {
        userRepository = new DummyDataAccessObject();
        mockPresenter = mock(SignupOutputBoundary.class);
        signupInteractor = new SignupInteractor(userRepository, mockPresenter);
    }

    @AfterEach
    void tearDown() {
        userRepository.clearAllUsers();
    }

    @Test
    void testExecute_SuccessfulStudentRegistration() {
        // Arrange
        SignupInputData inputData = new SignupInputData(
                "newstudent", "password123", "Student", "John Doe"
        );

        // Act
        signupInteractor.execute(inputData);

        // Assert
        verify(mockPresenter).prepareSuccessView(any(SignupOutputData.class));
        assertTrue(userRepository.existsByName("newstudent"));
        User savedUser = userRepository.get("newstudent");
        assertInstanceOf(Student.class, savedUser);
        assertEquals("newstudent", userRepository.getCurrentUsername());
    }

    @Test
    void testExecute_SuccessfulInstructorRegistration() {
        // Arrange
        SignupInputData inputData = new SignupInputData(
                "newinstructor", "password123", "Instructor", "Dr. Smith"
        );

        // Act
        signupInteractor.execute(inputData);

        // Assert
        verify(mockPresenter).prepareSuccessView(any(SignupOutputData.class));
        assertTrue(userRepository.existsByName("newinstructor"));
        User savedUser = userRepository.get("newinstructor");
        assertInstanceOf(Instructor.class, savedUser);
    }

    @Test
    void testExecute_DuplicateUsername() {
        // Arrange - Create a user first
        User existingUser = new Student("existinguser", "password");
        userRepository.save(existingUser);

        SignupInputData inputData = new SignupInputData(
                "existinguser", "newpassword", "Student", "Jane Doe"
        );

        // Act
        signupInteractor.execute(inputData);

        // Assert
        verify(mockPresenter).prepareFailView("Username 'existinguser' already exists. Please choose a different username.");
    }

    @Test
    void testExecute_EmptyUsername() {
        // Arrange
        SignupInputData inputData = new SignupInputData(
                "", "password123", "Student", "John Doe"
        );

        // Act
        signupInteractor.execute(inputData);

        // Assert
        verify(mockPresenter).prepareFailView("Username cannot be empty.");
    }

    @Test
    void testExecute_ShortUsername() {
        // Arrange
        SignupInputData inputData = new SignupInputData(
                "ab", "password123", "Student", "John Doe"
        );

        // Act
        signupInteractor.execute(inputData);

        // Assert
        verify(mockPresenter).prepareFailView("Username must be at least 3 characters long.");
    }

    @Test
    void testExecute_ShortPassword() {
        // Arrange
        SignupInputData inputData = new SignupInputData(
                "newuser", "123", "Student", "John Doe"
        );

        // Act
        signupInteractor.execute(inputData);

        // Assert
        verify(mockPresenter).prepareFailView("Password must be at least 6 characters long.");
    }

    @Test
    void testExecute_EmptyFullName() {
        // Arrange
        SignupInputData inputData = new SignupInputData(
                "newuser", "password123", "Student", ""
        );

        // Act
        signupInteractor.execute(inputData);

        // Assert
        verify(mockPresenter).prepareFailView("Full name cannot be empty.");
    }

    @Test
    void testExecute_InvalidRole() {
        // Arrange
        SignupInputData inputData = new SignupInputData(
                "newuser", "password123", "InvalidRole", "John Doe"
        );

        // Act
        signupInteractor.execute(inputData);

        // Assert
        verify(mockPresenter).prepareFailView("Invalid user role. Must be 'Student' or 'Instructor'.");
    }
}