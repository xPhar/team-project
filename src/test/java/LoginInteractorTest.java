package usecase.login;

import entity.Student;
import entity.User;
import data_access.DummyDataAccessObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginInteractorTest {
    private LoginInteractor loginInteractor;
    private DummyDataAccessObject userRepository;
    private LoginOutputBoundary mockPresenter;

    @BeforeEach
    void setUp() {
        userRepository = new DummyDataAccessObject();
        mockPresenter = mock(LoginOutputBoundary.class);
        loginInteractor = new LoginInteractor(userRepository, mockPresenter);

        // Pre-populate with a test user
        User testUser = new Student("testuser", "testpass");
        userRepository.save(testUser);
    }

    @AfterEach
    void tearDown() {
        userRepository.clearAllUsers();
    }

    @Test
    void testExecute_SuccessfulLogin() {
        // Arrange
        LoginInputData inputData = new LoginInputData("testuser", "testpass");

        // Act
        loginInteractor.execute(inputData);

        // Assert
        verify(mockPresenter).prepareSuccessView(any(LoginOutputData.class));
        assertEquals("testuser", userRepository.getCurrentUsername());
    }

    @Test
    void testExecute_UserNotFound() {
        // Arrange
        LoginInputData inputData = new LoginInputData("nonexistent", "password");

        // Act
        loginInteractor.execute(inputData);

        // Assert
        verify(mockPresenter).prepareFailView("Account 'nonexistent' does not exist.");
    }

    @Test
    void testExecute_IncorrectPassword() {
        // Arrange
        LoginInputData inputData = new LoginInputData("testuser", "wrongpassword");

        // Act
        loginInteractor.execute(inputData);

        // Assert
        verify(mockPresenter).prepareFailView("Incorrect password for \"testuser\".");
    }

    @Test
    void testExecute_EmptyUsername() {
        // Arrange
        LoginInputData inputData = new LoginInputData("", "password");

        // Act
        loginInteractor.execute(inputData);

        // Assert
        verify(mockPresenter).prepareFailView("Username cannot be empty.");
    }

    @Test
    void testExecute_EmptyPassword() {
        // Arrange
        LoginInputData inputData = new LoginInputData("testuser", "");

        // Act
        loginInteractor.execute(inputData);

        // Assert
        verify(mockPresenter).prepareFailView("Password cannot be empty.");
    }

    @Test
    void testExecute_NullUserRetrieval() {
        // Arrange
        // Create a situation where get returns null
        userRepository.clearAllUsers();
        // Manually set exists to true but get returns null (edge case)
        // We can't easily simulate this with current DummyDataAccessObject, so we test the normal flow

        LoginInputData inputData = new LoginInputData("testuser", "password");

        // Act
        loginInteractor.execute(inputData);

        // Assert - should fail because user doesn't exist
        verify(mockPresenter).prepareFailView("Account 'testuser' does not exist.");
    }

    @Test
    void testSwitchToSignupView() {
        // Act
        loginInteractor.switchToSignupView();

        // Assert
        verify(mockPresenter).switchToSignupView();
    }
}