package usecase.submit;

import data_access.FakeUserDataAccessObject;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SubmitInteractorTest {

    public static final String FailMsg = "SubmitInteractor failed in test!";

    @Test
    void successCase() {
        @SuppressWarnings("ConstantConditions")
        File file = new File(getClass().getResource("/submitCaseTestFile1.txt").getPath());

        SubmitInputData inputData = new SubmitInputData(LocalDateTime.now(), file);
        SubmitUserDataAccessInterface fakeDAO = new FakeUserDataAccessObject(false, false);

        SubmitOutputBoundary presenter = new SubmitOutputBoundary() {
            @Override
            public void prepareSuccessView(SubmitOutputData pack) {
                assertEquals(SubmitInteractor.SUCCESS_MSG, pack.getOutputMsg());
            }

            @Override
            public void prepareFailureView(SubmitOutputData submitOutputData) {
                fail(FailMsg);
            }

            /**
             *
             */
            @Override
            public void switchToLoggedInView() {}

        };

        SubmitInputBoundary interactor = new SubmitInteractor(fakeDAO, presenter);
        interactor.execute(inputData);
    }
    @Test
    void deadlineFailCase() {
        @SuppressWarnings("ConstantConditions")
        File file = new File(getClass().getResource("/submitCaseTestFile1.txt").getPath());

        SubmitInputData inputData = new SubmitInputData(LocalDateTime.now(), file);
        SubmitUserDataAccessInterface fakeDAO = new FakeUserDataAccessObject(true, false);

        SubmitOutputBoundary presenter = new SubmitOutputBoundary() {
            @Override
            public void prepareSuccessView(SubmitOutputData pack) {
                fail(FailMsg);
            }

            @Override
            public void prepareFailureView(SubmitOutputData pack) {
                assertEquals(SubmitInteractor.DDL_PASSED_MSG, pack.getOutputMsg());
            }

            /**
             *
             */
            @Override
            public void switchToLoggedInView() {}

        };

        SubmitInputBoundary interactor = new SubmitInteractor(fakeDAO, presenter);
        interactor.execute(inputData);
    }

    @Test
    void networkFailCase() {
        @SuppressWarnings("ConstantConditions")
        File file = new File(getClass().getResource("/submitCaseTestFile1.txt").getPath());

        SubmitInputData inputData = new SubmitInputData(LocalDateTime.now(), file);
        SubmitUserDataAccessInterface fakeDAO = new FakeUserDataAccessObject(false, true);

        SubmitOutputBoundary presenter = new SubmitOutputBoundary() {
            @Override
            public void prepareSuccessView(SubmitOutputData pack) {
                fail(FailMsg);
            }

            @Override
            public void prepareFailureView(SubmitOutputData pack) {
                assertEquals(SubmitInteractor.NETWORK_ERROR_MSG, pack.getOutputMsg());
            }

            /**
             *
             */
            @Override
            public void switchToLoggedInView() {}

        };

        SubmitInputBoundary interactor = new SubmitInteractor(fakeDAO, presenter);
        interactor.execute(inputData);
    }

    @Test
    void FileTypeFailCase() {
        @SuppressWarnings("ConstantConditions")
        File file = new File(getClass().getResource("/submitCaseTestFile2.abcdefg").getPath());
        SubmitInputData inputData = new SubmitInputData(LocalDateTime.now(), file);
        SubmitUserDataAccessInterface fakeDAO = new FakeUserDataAccessObject(false, true);

        SubmitOutputBoundary presenter = new SubmitOutputBoundary() {
            @Override
            public void prepareSuccessView(SubmitOutputData pack) {
                fail(FailMsg);
            }

            @Override
            public void prepareFailureView(SubmitOutputData pack) {
                assertEquals(SubmitInteractor.WRONG_FILE_MSG, pack.getOutputMsg());
            }

            /**
             *
             */
            @Override
            public void switchToLoggedInView() {}

        };

        SubmitInputBoundary interactor = new SubmitInteractor(fakeDAO, presenter);
        interactor.execute(inputData);
    }

    @Test
    void backCase() {
        SubmitUserDataAccessInterface fakeDAO = new FakeUserDataAccessObject(false, true);

        final SubmitInputBoundary interactor = getSubmitInputBoundary(fakeDAO);
        interactor.backToLoggedInView();

    }

    @NotNull
    private SubmitInputBoundary getSubmitInputBoundary(SubmitUserDataAccessInterface fakeDAO) {
        SubmitOutputBoundary presenter = new SubmitOutputBoundary() {
            @Override
            public void prepareSuccessView(SubmitOutputData pack) {
                fail(FailMsg);
            }

            @Override
            public void prepareFailureView(SubmitOutputData pack) {
                fail(FailMsg);
            }

            @Override
            public void switchToLoggedInView() {
            assertTrue(true);}
        };

        return new SubmitInteractor(fakeDAO, presenter);
    }
}
