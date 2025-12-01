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
        interactor = new CreateAssignmentInteractor(dataAccess, presenter, assignmentsBoundary);
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

        assertTrue(assignmentsBoundary.executeCalled, "Assignments view refresh should be triggered.");

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

