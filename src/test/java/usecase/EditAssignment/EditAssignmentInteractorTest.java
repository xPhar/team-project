package usecase.EditAssignment;

import entity.Assignment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EditAssignmentInteractorTest {

    private TestEditAssignmentDAO dataAccess;
    private TestEditAssignmentPresenter presenter;
    private EditAssignmentInteractor interactor;


    @BeforeEach
    void setup() {
        dataAccess = new TestEditAssignmentDAO();
        presenter = new TestEditAssignmentPresenter();
        interactor = new EditAssignmentInteractor(dataAccess, presenter);
    }
    @Test
    void testExecute_Success() {
        LocalDateTime newDueDate = LocalDateTime.now().plusWeeks(2);
        String originalName = "Old Final Essay";
        String newName = "New Final Project";
        String courseCode = "CSC207";
        List<String> newFileTypes = List.of("zip", "rar");

        EditAssignmentInputData inputData = new EditAssignmentInputData(
                originalName,
                newName,
                courseCode,
                "Updated description for the project.",
                newDueDate,
                newFileTypes
        );

        interactor.execute(inputData);

        assertTrue(presenter.successCalled, "Presenter's success view should be called.");
        assertFalse(presenter.failCalled, "Failure view should NOT be called.");

        assertTrue(dataAccess.updateCalled, "DAO update method should be called.");

        assertEquals(courseCode, dataAccess.courseCodePassed);
        assertEquals(originalName, dataAccess.originalNamePassed);

        Assignment updatedAssignment = dataAccess.updatedAssignment;
        assertNotNull(updatedAssignment, "Updated assignment object should not be null.");
        assertEquals(newName, updatedAssignment.getName());
        assertEquals(newDueDate, updatedAssignment.getDueDate());
        assertEquals(newFileTypes, updatedAssignment.getSupportedFileTypes());
        assertEquals("Updated description for the project.", updatedAssignment.getDescription());
    }

    @Test
    void testExecute_Failure_DataAccessError() {
        dataAccess.shouldThrowException = true;
        dataAccess.exceptionMessage = "Assignment not found in database.";

        EditAssignmentInputData inputData = new EditAssignmentInputData(
                "NonExistent",
                "New Name",
                "CSC207",
                "Description",
                LocalDateTime.now().plusDays(1),
                List.of("pdf")
        );

        interactor.execute(inputData);

        assertTrue(presenter.failCalled, "Presenter's failure view should be called due to exception.");
        assertEquals("Assignment not found in database.", presenter.failMessage);

        assertFalse(presenter.successCalled);
    }

    static class TestEditAssignmentDAO implements EditAssignmentDataAccessInterface {
        public boolean updateCalled = false;
        public String courseCodePassed = null;
        public String originalNamePassed = null;
        public Assignment updatedAssignment = null;

        public boolean shouldThrowException = false;
        public String exceptionMessage = "";

        @Override
        public void updateAssignment(String courseCode, String originalAssignmentName, Assignment assignment) {
            updateCalled = true;
            courseCodePassed = courseCode;
            originalNamePassed = originalAssignmentName;
            updatedAssignment = assignment;

            if (shouldThrowException) {
                throw new RuntimeException(exceptionMessage);
            }
        }
    }

    static class TestEditAssignmentPresenter implements EditAssignmentOutputBoundary {
        public boolean successCalled = false;
        public boolean failCalled = false;
        public String failMessage = null;

        @Override
        public void prepareSuccessView() {
            successCalled = true;
        }

        @Override
        public void prepareFailureView(String errorMessage) {
            failCalled = true;
            failMessage = errorMessage;
        }
    }
}
