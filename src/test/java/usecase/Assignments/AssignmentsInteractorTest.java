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

        assertTrue(presenter.successCalled);
        assertTrue(presenter.outputData.isInstructor());

        assertEquals("CSC207", presenter.outputData.getCourseName());

        List<AssignmentDTO> dtos = presenter.outputData.getAssignments();
        assertEquals(3, dtos.size());

        assertEquals("A_First", dtos.get(0).getName());
        assertEquals("Z_Last", dtos.get(1).getName());
        assertEquals("C_Null", dtos.get(2).getName());
    }

    @Test
    void testExecute_SuccessAsStudent() {
        dao.assignments = Collections.emptyList();
        dao.currentUserType = USER_TYPE.STUDENT;

        interactor.execute(new AssignmentsInputData());

        assertTrue(presenter.successCalled);
        assertFalse(presenter.outputData.isInstructor());
        assertEquals("CSC207", presenter.outputData.getCourseName());
    }

    @Test
    void testExecute_Failure() {
        dao.shouldThrowException = true;
        dao.exceptionMessage = "Database connection failed";

        interactor.execute(new AssignmentsInputData());

        assertTrue(presenter.failCalled);
        assertFalse(presenter.successCalled);
        assertEquals("Error loading assignments: Database connection failed", presenter.failMessage);
    }

    @Test
    void testSwitchToSubmissionListView_SubmissionsGradedAndPending() {
        String assignmentName = "FinalProject";
        Assignment assignment = Assignment.builder()
                .name(assignmentName)
                .supportedFileTypes(Collections.emptyList())
                .build();
        dao.assignmentToReturn = assignment;

        LocalDateTime subTime = FIXED_TIME.minusHours(1);

        Submission gradedSub = new Submission( "Alice", subTime, "file", "data", 95.55, Submission.Status.GRADED, "ok");
        Submission pendingSub = new Submission("Bob", subTime, "file", "data", 0.0, Submission.Status.UNDER_REVIEW, "");
        dao.submissions = Arrays.asList(gradedSub, pendingSub);

        interactor.switchToSubmissionListView(assignmentName);

        assertTrue(presenter.switchToSubmissionListViewCalled);
        assertEquals(assignment, dao.activeAssignmentSet);

        String[][] submissionTable = presenter.outputData.getSubmissions();
        assertEquals(2, submissionTable.length);

        String expectedTime = subTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"));

        assertEquals("Alice", submissionTable[0][0]);
        assertEquals(expectedTime, submissionTable[0][1]);
        assertEquals("95.6", submissionTable[0][2]);

        assertEquals("Bob", submissionTable[1][0]);
        assertEquals(expectedTime, submissionTable[1][1]);
        assertEquals("pending", submissionTable[1][2]);
    }

    @Test
    void testSwitchToCreateAssignmentView() {
        interactor.switchToCreateAssignmentView();
        assertTrue(presenter.switchToCreateAssignmentViewCalled);
    }

    @Test
    void testSwitchToSubmitView() {
        interactor.switchToSubmitView();
        assertTrue(presenter.switchToSubmitViewCalled);
    }

    @Test
    void testSwitchToResubmitView() {
        interactor.switchToResubmitView();
        assertTrue(presenter.switchToResubmitViewCalled);
    }

    @Test
    void testSwitchToLoginView() {
        dao.currentUserType = USER_TYPE.STUDENT;
        dao.currentUserName = "LoggedInStudent";

        interactor.switchToLoginView();

        assertTrue(presenter.switchToLoginViewCalled);
        assertEquals("LoggedInStudent", presenter.usernameForLogin, "Username should be passed and stored in presenter.");
        assertTrue(dao.sessionReset);
    }

    static class TestAssignmentsDAO implements AssignmentsDataAccessInterface {
        List<Assignment> assignments = Collections.emptyList();
        USER_TYPE currentUserType = USER_TYPE.STUDENT;
        String currentUserName = "TestUser";
        String courseCode = "";
        boolean shouldThrowException = false;
        String exceptionMessage = "";
        Assignment assignmentToReturn = null;
        Assignment activeAssignmentSet = null;
        List<Submission> submissions = Collections.emptyList();
        boolean sessionReset = false;


        @Override
        public List<Assignment> getAssignments() {
            if (shouldThrowException) {
                throw new RuntimeException(exceptionMessage);
            }
            return assignments;
        }

        @Override
        public User getCurrentUser() {
            return new User(currentUserName, "pass", "test", "user", currentUserType);
        }

        @Override
        public String getCourseCode() {
            return courseCode;
        }

        @Override
        public Assignment getAssignment(String assignmentName) {
            return assignmentToReturn;
        }

        @Override
        public void setActiveAssignment(Assignment assignment) {
            this.activeAssignmentSet = assignment;
        }

        @Override
        public List<Submission> getSubmissionList(Assignment assignment) {
            return submissions;
        }

        @Override
        public void resetSession() {
            this.sessionReset = true;
            activeAssignmentSet = null;
        }
    }

    static class TestAssignmentsPresenter implements AssignmentsOutputBoundary {
        boolean successCalled = false;
        boolean failCalled = false;
        boolean switchToCreateAssignmentViewCalled = false;
        boolean switchToSubmitViewCalled = false;
        boolean switchToResubmitViewCalled = false;
        boolean switchToSubmissionListViewCalled = false;
        boolean switchToLoginViewCalled = false;

        String failMessage;
        AssignmentsOutputData outputData;
        String usernameForLogin;

        @Override
        public void prepareSuccessView(AssignmentsOutputData outputData) {
            successCalled = true;
            this.outputData = outputData;
        }

        @Override
        public void prepareFailureView(String errorMessage) {
            failCalled = true;
            this.failMessage = errorMessage;
        }

        @Override
        public void switchToCreateAssignmentView() {
            switchToCreateAssignmentViewCalled = true;
        }

        @Override
        public void switchToSubmitView() {
            switchToSubmitViewCalled = true;
        }

        @Override
        public void switchToResubmitView() {
            switchToResubmitViewCalled = true;
        }

        @Override
        public void switchToSubmissionListView(AssignmentsOutputData outputData) {
            switchToSubmissionListViewCalled = true;
            this.outputData = outputData;
        }

        @Override
        public void switchToLoginView(AssignmentsOutputData outputData) {
            switchToLoginViewCalled = true;
            this.outputData = outputData;
            this.usernameForLogin = outputData.getUsername();
        }
    }
}
