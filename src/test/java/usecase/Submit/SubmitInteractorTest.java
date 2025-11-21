package usecase.Submit;

import app.Session;
import data_access.FakeUserDataAccessObject;
import data_access.ImposibleUserDataAccessObject;
import entity.AssignmentBuilder;
import entity.Course;
import entity.Student;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class SubmitInteractorTest {

    private Session generateDummySession(boolean ddlPassed) {
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

        return session;
    }

    @Test
    void successCase() {
        @SuppressWarnings("ConstantConditions")
        File file = new File(getClass().getResource("/submitCaseTestFile1.txt").getPath());

        SubmitInputData inputData = new SubmitInputData(LocalDateTime.now(), file);
        SubmitUserDataAccessInterface fakeDAO = new FakeUserDataAccessObject();

        SubmitOutputBoundary successPresenter = new SubmitOutputBoundary() {
            @Override
            public void prepareSuccessView(SubmitOutputData pack) {
                assertEquals("Successfully submitted!", pack.getOutputMsg());
            }

            @Override
            public void prepareFailureView(SubmitOutputData submitOutputData) {
                fail("SubmitInteractor failed in success case!");
            }

        };

        Session session = generateDummySession(false);
        SubmitInputBoundary interactor = new SubmitInteractor(fakeDAO, successPresenter, session);
        interactor.execute(inputData);
    }
    @Test
    void deadlineFailCase() {
        @SuppressWarnings("ConstantConditions")
        File file = new File(getClass().getResource("/submitCaseTestFile1.txt").getPath());

        SubmitInputData inputData = new SubmitInputData(LocalDateTime.now(), file);
        SubmitUserDataAccessInterface fakeDAO = new FakeUserDataAccessObject();

        SubmitOutputBoundary successPresenter = new SubmitOutputBoundary() {
            @Override
            public void prepareSuccessView(SubmitOutputData pack) {
                fail("SubmitInteractor failed in success case!");
            }

            @Override
            public void prepareFailureView(SubmitOutputData pack) {
                assertEquals("Deadline is passed, you cannot submit", pack.getOutputMsg());
            }

        };

        Session session = generateDummySession(true);
        SubmitInputBoundary interactor = new SubmitInteractor(fakeDAO, successPresenter, session);
        interactor.execute(inputData);
    }

    @Test
    void networkFailCase() {
        @SuppressWarnings("ConstantConditions")
        File file = new File(getClass().getResource("/submitCaseTestFile1.txt").getPath());

        SubmitInputData inputData = new SubmitInputData(LocalDateTime.now(), file);
        SubmitUserDataAccessInterface fakeDAO = new ImposibleUserDataAccessObject();

        SubmitOutputBoundary successPresenter = new SubmitOutputBoundary() {
            @Override
            public void prepareSuccessView(SubmitOutputData pack) {
                fail("SubmitInteractor failed in success case!");
            }

            @Override
            public void prepareFailureView(SubmitOutputData pack) {
                assertEquals("Network Error! Please try again later.", pack.getOutputMsg());
            }

        };

        Session session = generateDummySession(false);
        SubmitInputBoundary interactor = new SubmitInteractor(fakeDAO, successPresenter, session);
        interactor.execute(inputData);
    }
}

