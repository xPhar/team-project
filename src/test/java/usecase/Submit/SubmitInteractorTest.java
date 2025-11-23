package usecase.Submit;

import data_access.FakeUserDataAccessObject;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class SubmitInteractorTest {

    @Test
    void successCase() {
        @SuppressWarnings("ConstantConditions")
        File file = new File(getClass().getResource("/submitCaseTestFile1.txt").getPath());

        SubmitInputData inputData = new SubmitInputData(LocalDateTime.now(), file);
        SubmitUserDataAccessInterface fakeDAO = new FakeUserDataAccessObject(false, false);

        SubmitOutputBoundary presenter = new SubmitOutputBoundary() {
            @Override
            public void prepareSuccessView(SubmitOutputData pack) {
                assertEquals("Successfully submitted!", pack.getOutputMsg());
            }

            @Override
            public void prepareFailureView(SubmitOutputData submitOutputData) {
                fail("SubmitInteractor failed in success case!");
            }

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
                fail("SubmitInteractor failed in success case!");
            }

            @Override
            public void prepareFailureView(SubmitOutputData pack) {
                assertEquals("Deadline is passed, you cannot submit", pack.getOutputMsg());
            }

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
                fail("SubmitInteractor failed in success case!");
            }

            @Override
            public void prepareFailureView(SubmitOutputData pack) {
                assertEquals("Network Error! Please try again later.", pack.getOutputMsg());
            }

        };

        SubmitInputBoundary interactor = new SubmitInteractor(fakeDAO, presenter);
        interactor.execute(inputData);
    }
}
