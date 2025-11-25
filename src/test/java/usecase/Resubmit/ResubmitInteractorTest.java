package usecase.Resubmit;

import data_access.FakeUserDataAccessObject;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ResubmitInteractorTest {

    @Test
    void successCase(){

        ResubmitInputData inputData = new ResubmitInputData(LocalDateTime.now());

        ResubmitOutputBoundary presenter = new ResubmitOutputBoundary() {
            /**
             * Prepares the success view for the Resubmit Use Case. This is expected
             */
            @Override
            public void prepareSuccessView() {
                switchToSubmitView();
            }

            /**
             * Prepares the failure view for the Resubmit Use Case. This should not be reached
             *
             * @param errorMessage the explanation of the failure
             */
            @Override
            public void prepareFailView(String errorMessage) {
                fail("ResubmitInteractor failed in success case!");
            }

            /**
             * Switches to the Submit View. Here just assert true
             */
            @Override
            public void switchToSubmitView() {
                assertTrue(true);
            }
        };

        FakeUserDataAccessObject dummyDAO = new FakeUserDataAccessObject(false, false);
        ResubmitInputBoundary interactor = new ResubmitInteractor(presenter, dummyDAO);
        interactor.execute(inputData);
    }

    @Test
    void failCase(){

        ResubmitInputData inputData = new ResubmitInputData(LocalDateTime.now());

        ResubmitOutputBoundary presenter = new ResubmitOutputBoundary() {
            /**
             * Prepares the success view for the Resubmit Use Case. This is expected
             */
            @Override
            public void prepareSuccessView() {
                switchToSubmitView();
            }

            /**
             * Prepares the failure view for the Resubmit Use Case. This should not be reached
             *
             * @param errorMessage the explanation of the failure
             */
            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("DDL is passed, you cannot resubmit your assignment!", errorMessage);
            }

            /**
             * Switches to the Submit View. Here just assert true
             */
            @Override
            public void switchToSubmitView() {
                fail("ResubmitInteractor failed in success case!");
            }
        };

        FakeUserDataAccessObject dummyDAO = new FakeUserDataAccessObject(true, false);
        ResubmitInputBoundary interactor = new ResubmitInteractor(presenter, dummyDAO);
        interactor.execute(inputData);
    }
}
