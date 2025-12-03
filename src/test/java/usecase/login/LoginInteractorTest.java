package usecase.login;

import data_access.FakeUserDataAccessObject;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LoginInteractorTest {

    @Test
    void testStudent1() {
        String username = "FakeStudent1";
        String password = "password";
        LoginInputData inputData = new LoginInputData(username, password);
        LocalDateTime time = LocalDateTime.now();
        LoginDataAccessInterface fakeDAO = new FakeUserDataAccessObject(time);

        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData response) {
                // assert that values are expected
                assertEquals(username, response.getUsername(), "Usernames don't match.");
                assertEquals("student", response.getUserType(), "User type is incorrect.");
                Object[][] expectedAssignments = {
                        {"FakeAssignment1", time.plusMinutes(30), "-", "✔"},
                        {"FakeAssignment2", time, "21.0", "✔"}
                };

                Object[][] actualAssignments = response.getAssignments();
                for (int i = 0; i < expectedAssignments.length; i++) {
                    assertEquals(actualAssignments[i][0], expectedAssignments[i][0], "Assignment names don't match.");
                    assertEquals(actualAssignments[i][1], expectedAssignments[i][1], "Due dates don't match.");
                    assertEquals(actualAssignments[i][2], expectedAssignments[i][2], "Grades don't match.");
                    assertEquals(actualAssignments[i][3], expectedAssignments[i][3], "Submission Statuses don't match.");
                }
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Interactor gave error: " + errorMessage + "\nBut should have been successful.");
            }

            @Override
            public void switchToSignupView() {
                fail("Interactor called switchToSignupView instead of prepareSuccessView.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(fakeDAO, presenter);
        interactor.execute(inputData);
    }

    @Test
    void testStudent2() {
        String username = "FakeStudent2";
        String password = "password";
        LoginInputData inputData = new LoginInputData(username, password);
        LocalDateTime time = LocalDateTime.now();
        LoginDataAccessInterface fakeDAO = new FakeUserDataAccessObject(time);

        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData response) {
                // assert that values are expected
                assertEquals(username, response.getUsername(), "Usernames don't match.");
                assertEquals("student", response.getUserType(), "User type is incorrect.");
                Object[][] expectedAssignments = {
                        {"FakeAssignment1", time.plusMinutes(30), "", "✖"},
                        {"FakeAssignment2", time, "-", "✔"}
                };

                Object[][] actualAssignments = response.getAssignments();
                for (int i = 0; i < expectedAssignments.length; i++) {
                    assertEquals(actualAssignments[i][0], expectedAssignments[i][0], "Assignment names don't match.");
                    assertEquals(actualAssignments[i][1], expectedAssignments[i][1], "Due dates don't match.");
                    assertEquals(actualAssignments[i][2], expectedAssignments[i][2], "Grades don't match.");
                    assertEquals(actualAssignments[i][3], expectedAssignments[i][3], "Submission Statuses don't match.");
                }
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Interactor gave error: " + errorMessage + "\nBut should have been successful.");
            }

            @Override
            public void switchToSignupView() {
                fail("Interactor called switchToSignupView instead of prepareSuccessView.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(fakeDAO, presenter);
        interactor.execute(inputData);
    }

    @Test
    void testInstructor() {
        String username = "FakeInstructor";
        String password = "password";
        LoginInputData inputData = new LoginInputData(username, password);
        LocalDateTime time = LocalDateTime.now();
        LoginDataAccessInterface fakeDAO = new FakeUserDataAccessObject(time);

        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData response) {
                // assert that values are expected
                assertEquals(username, response.getUsername(), "Usernames don't match.");
                assertEquals("instructor", response.getUserType(), "User type is incorrect.");

                assertNull(response.getAssignments());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Interactor gave error: " + errorMessage + "\nBut should have been successful.");
            }

            @Override
            public void switchToSignupView() {
                fail("Interactor called switchToSignupView instead of prepareSuccessView.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(fakeDAO, presenter);
        interactor.execute(inputData);
    }

    @Test
    void testSignupSwitch() {
        LoginDataAccessInterface fakeDAO = new FakeUserDataAccessObject(LocalDateTime.now());
        LoginOutputBoundary presenter = new LoginOutputBoundary() {

            @Override
            public void prepareSuccessView(LoginOutputData outputData) {
                fail("Interactor called prepareSuccessView instead of switchToSignupView.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Interactor gave error: " + errorMessage + "\nBut should have been successful.");
            }

            @Override
            public void switchToSignupView() {
                assertTrue(true);
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(fakeDAO, presenter);
        interactor.switchToSignupView();
    }

    @Test
    void testEmptyUsername() {
        String username = "";
        String password = "password";
        LoginInputData inputData = new LoginInputData(username, password);
        LocalDateTime time = LocalDateTime.now();
        LoginDataAccessInterface fakeDAO = new FakeUserDataAccessObject(time);

        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData response) {
                fail("Interactor accepted login info when it should've failed.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Username cannot be empty.", errorMessage);
            }

            @Override
            public void switchToSignupView() {
                fail("Interactor called switchToSignupView instead of prepareSuccessView.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(fakeDAO, presenter);
        interactor.execute(inputData);
    }

    @Test
    void testEmptyPassword() {
        String username = "someUsername";
        String password = "";
        LoginInputData inputData = new LoginInputData(username, password);
        LocalDateTime time = LocalDateTime.now();
        LoginDataAccessInterface fakeDAO = new FakeUserDataAccessObject(time);

        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData response) {
                fail("Interactor accepted login info when it should've failed.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Password cannot be empty.", errorMessage);
            }

            @Override
            public void switchToSignupView() {
                fail("Interactor called switchToSignupView instead of prepareSuccessView.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(fakeDAO, presenter);
        interactor.execute(inputData);
    }

    @Test
    void testNonExistingUser() {
        String username = "notARealUser";
        String password = "somePassword";
        LoginInputData inputData = new LoginInputData(username, password);
        LocalDateTime time = LocalDateTime.now();
        LoginDataAccessInterface fakeDAO = new FakeUserDataAccessObject(time);

        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData response) {
                fail("Interactor accepted login info when it should've failed.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Account '" + username + "' does not exist.", errorMessage);
            }

            @Override
            public void switchToSignupView() {
                fail("Interactor called switchToSignupView instead of prepareSuccessView.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(fakeDAO, presenter);
        interactor.execute(inputData);
    }

    @Test
    void testDataAccessException() {
        String username = "userThatThrowsException";
        String password = "somePassword";
        LoginInputData inputData = new LoginInputData(username, password);
        LocalDateTime time = LocalDateTime.now();
        LoginDataAccessInterface fakeDAO = new FakeUserDataAccessObject(time);

        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData response) {
                fail("Interactor accepted login info when it should've failed.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("An error has occurred, please try again.\n" +
                        "Error message: User data is mangled. Please try a different account.",
                        errorMessage);
            }

            @Override
            public void switchToSignupView() {
                fail("Interactor called switchToSignupView instead of prepareSuccessView.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(fakeDAO, presenter);
        interactor.execute(inputData);
    }

    @Test
    void testIncorrectPassword() {
        String username = "FakeInstructor";
        String password = "notTheRightPassword";
        LoginInputData inputData = new LoginInputData(username, password);
        LocalDateTime time = LocalDateTime.now();
        LoginDataAccessInterface fakeDAO = new FakeUserDataAccessObject(time);

        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData response) {
                fail("Interactor accepted login info when it should've failed.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Incorrect password for \"" + username + "\".", errorMessage);
            }

            @Override
            public void switchToSignupView() {
                fail("Interactor called switchToSignupView instead of prepareSuccessView.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(fakeDAO, presenter);
        interactor.execute(inputData);
    }
}
