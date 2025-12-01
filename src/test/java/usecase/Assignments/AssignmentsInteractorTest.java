package usecase.Assignments;

import entity.Assignment;
import entity.Submission;
import entity.User;
import entity.User.USER_TYPE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentsInteractorTest {

    private TestAssignmentsDAO dao;
    private TestAssignmentsPresenter presenter;
    private AssignmentsInteractor interactor;
    private final LocalDateTime FIXED_TIME = LocalDateTime.of(2025, 12, 1, 10, 0, 0);


    @BeforeEach
    void setup() {
        dao = new TestAssignmentsDAO();
        presenter = new TestAssignmentsPresenter();
        interactor = new AssignmentsInteractor(dao, presenter);
        dao.courseCode = "CSC207";
    }

    @Test
    void testExecute_SuccessAsInstructor_VerifiesSorting() {
        Assignment a1_dueLater = Assignment.builder()
                .name("Z_Last")
                .dueDate(FIXED_TIME.plusDays(5))
                .supportedFileTypes(Collections.emptyList())
                .build();
        Assignment a2_dueFirst = Assignment.builder()
                .name("A_First")
                .dueDate(FIXED_TIME.plusDays(1))
                .supportedFileTypes(Collections.emptyList())
                .build();
        Assignment a3_dueNull = Assignment.builder()
                .name("C_Null")
                .dueDate(null)
                .supportedFileTypes(Collections.emptyList())
                .build();

        dao.assignments = Arrays.asList(a1_dueLater, a3_dueNull, a2_dueFirst);
        dao.currentUserType = USER_TYPE.INSTRUCTOR;

        interactor.execute(new AssignmentsInputData());

        assertTrue(presenter.successCalled, "Success view should be called.");
        assertTrue(presenter.outputData.isInstructor());

        assertEquals("CSC207", presenter.outputData.getCourseName());

        List<AssignmentDTO> dtos = presenter.outputData.getAssignments();
        assertEquals(3, dtos.size());

        assertEquals("A_First", dtos.get(0).getName(), "Earliest DDL should be first.");
        assertEquals("Z_Last", dtos.get(1).getName());
        assertEquals("C_Null", dtos.get(2).getName(), "Null DDL should be last.");
    }

    @Test
    void testExecute_SuccessAsStudent() {
        dao.assignments = Collections.emptyList();
        dao.currentUserType = USER_TYPE.STUDENT;

        interactor.execute(new AssignmentsInputData());

        assertTrue(presenter.successCalled);
        assertFalse(presenter.outputData.isInstructor(), "User type should be Student.");
        assertEquals("CSC207", presenter.outputData.getCourseName());
    }

