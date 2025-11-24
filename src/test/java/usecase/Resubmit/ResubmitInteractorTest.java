package usecase.Resubmit;

import entity.Session;
import entity.AssignmentBuilder;
import entity.Course;
import entity.Student;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ResubmitInteractorTest {
    private void generateDummySession(boolean ddlPassed) {
        LocalDateTime due = ddlPassed ? LocalDateTime.MIN:LocalDateTime.MAX;
        Session session = Session.getInstance();
        session.setUser(new Student("This is a test Name", "This is a test pwd"));
        session.setCourse(new Course("Course Name", "TEST101"));
        session.setAssignment(
                new AssignmentBuilder()
                        .dueDate(due)
                        .name("This is a test Name")
                        .build()
        );
    }

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

        generateDummySession(false);
        ResubmitInputBoundary interactor = new ResubmitInteractor(presenter);
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

        generateDummySession(true);
        ResubmitInputBoundary interactor = new ResubmitInteractor(presenter);
        interactor.execute(inputData);
    }
}
