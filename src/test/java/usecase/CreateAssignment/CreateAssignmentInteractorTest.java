package usecase.CreateAssignment;

import entity.Assignment;
import usecase.Assignments.AssignmentsInputBoundary;
import usecase.Assignments.AssignmentsInputData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateAssignmentInteractorTest {

    private TestCreateAssignmentDAO dataAccess;
    private TestCreateAssignmentPresenter presenter;
    private TestAssignmentsInputBoundary assignmentsBoundary;
    private CreateAssignmentInteractor interactor;


    @BeforeEach
    void setup() {
        dataAccess = new TestCreateAssignmentDAO();
        presenter = new TestCreateAssignmentPresenter();
        assignmentsBoundary = new TestAssignmentsInputBoundary();
        interactor = new CreateAssignmentInteractor(dataAccess, presenter);
    }

    @Test
    void testExecute_Success() {
        LocalDateTime dueDate = LocalDateTime.now().plusDays(7);
        CreateAssignmentInputData inputData = new CreateAssignmentInputData(
                "Final Essay", // name
                "Write a 1500 word essay.", // description
                dueDate, // dueDate
                0.5, // gracePeriod
                List.of("pdf", "doc"), // supportedFileTypes
                "CSC207" // courseCode
        );

        interactor.execute(inputData);

        assertTrue(presenter.successCalled, "Presenter's success view should be called.");

        assertTrue(dataAccess.saveCalled, "DAO save method should be called.");

        Assignment savedAssignment = dataAccess.savedAssignment;
        assertNotNull(savedAssignment, "Saved assignment object should not be null.");
        assertEquals("Final Essay", savedAssignment.getName());
        assertEquals(dueDate, savedAssignment.getDueDate());
        assertEquals("CSC207", dataAccess.courseCodePassed);
        assertNotNull(savedAssignment.getCreationDate(), "Creation date should be set.");
    }

    @Test
    void testExecute_Failure_EmptyName() {
        CreateAssignmentInputData inputData = new CreateAssignmentInputData(
                "",
                "Description",
                LocalDateTime.now().plusDays(7),
                0.5,
                List.of("pdf"),
                "CSC207"
        );

        interactor.execute(inputData);

        assertTrue(presenter.failCalled, "Presenter's failure view should be called for empty name.");
        assertEquals("Assignment name cannot be empty.", presenter.failMessage);

        assertFalse(dataAccess.saveCalled, "DAO save should NOT be called.");
        assertFalse(assignmentsBoundary.executeCalled, "Assignments refresh should NOT be called.");
    }

    @Test
    void testExecute_Failure_DataAccessError() {
        dataAccess.shouldThrowException = true;
        dataAccess.exceptionMessage = "Database write failed.";

        CreateAssignmentInputData inputData = new CreateAssignmentInputData(
                "Valid Name", // name
                "Description",
                LocalDateTime.now().plusDays(7),
                0.5, // gracePeriod
                List.of("pdf"), // supportedFileTypes
                "CSC207" // courseCode
        );

        interactor.execute(inputData);

        assertTrue(presenter.failCalled, "Presenter's failure view should be called due to exception.");
        assertEquals("Failed to create assignment: Database write failed.", presenter.failMessage);

        assertFalse(presenter.successCalled);
    }

    @Test
    void testSwitchToAssignmentView() {
        interactor.switchToAssignmentView();
        assertTrue(presenter.switchToAssignmentViewCalled, "Presenter's switch view method should be called.");
    }

    static class TestAssignmentsInputBoundary implements AssignmentsInputBoundary {
        public boolean executeCalled = false;
        @Override
        public void execute(AssignmentsInputData inputData) {
            executeCalled = true;
        }

        @Override
        public void switchToCreateAssignmentView() {
        }

        @Override
        public void switchToSubmitView() {
        }

        @Override
        public void switchToResubmitView() {
        }

        @Override
        public void switchToSubmissionListView(String assignmentName) {
        }

        @Override
        public void switchToLoginView() {
        }
    }

    static class TestCreateAssignmentDAO implements CreateAssignmentDataAccessInterface {
        public boolean saveCalled = false;
        public Assignment savedAssignment = null;
        public String courseCodePassed = null;
        public boolean shouldThrowException = false;
        public String exceptionMessage = "";

        @Override
        public void saveAssignment(String courseCode, Assignment assignment) {
            saveCalled = true;
            courseCodePassed = courseCode;
            savedAssignment = assignment;
            if (shouldThrowException) {
                throw new RuntimeException(exceptionMessage);
            }
        }
    }

    static class TestCreateAssignmentPresenter implements CreateAssignmentOutputBoundary {
        public boolean successCalled = false;
        public boolean failCalled = false;
        public String failMessage = null;
        public boolean switchToAssignmentViewCalled = false;

        @Override
        public void prepareSuccessView(CreateAssignmentOutputData outputData) {
            successCalled = true;
        }

        @Override
        public void prepareFailureView(String errorMessage) {
            failCalled = true;
            failMessage = errorMessage;
        }

        @Override
        public void switchToAssignmentView() {
            switchToAssignmentViewCalled = true;
        }
    }
}