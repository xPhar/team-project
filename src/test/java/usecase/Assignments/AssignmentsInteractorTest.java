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

        // 2. Act
        interactor.execute(new AssignmentsInputData());

        // 3. Assert
        assertTrue(presenter.successCalled, "Success view should be called.");
        assertTrue(presenter.outputData.isInstructor());

        // 修正：使用 getCourseName() 代替 getCourseCode()
        assertEquals("CSC207", presenter.outputData.getCourseName());

        List<AssignmentDTO> dtos = presenter.outputData.getAssignments();
        assertEquals(3, dtos.size());

        // 验证排序：最早到期 -> 较晚到期 -> DDL为空
        assertEquals("A_First", dtos.get(0).getName(), "Earliest DDL should be first.");
        assertEquals("Z_Last", dtos.get(1).getName());
        assertEquals("C_Null", dtos.get(2).getName(), "Null DDL should be last.");
    }

    // --- Test 2: execute() 成功场景 (作为学生) ---
    @Test
    void testExecute_SuccessAsStudent() {
        // 1. Arrange
        dao.assignments = Collections.emptyList();
        dao.currentUserType = USER_TYPE.STUDENT;

        // 2. Act
        interactor.execute(new AssignmentsInputData());

        // 3. Assert
        assertTrue(presenter.successCalled);
        assertFalse(presenter.outputData.isInstructor(), "User type should be Student.");
        // 修正：使用 getCourseName() 代替 getCourseCode()
        assertEquals("CSC207", presenter.outputData.getCourseName());
    }

    // --- Test 3: execute() 失败场景 (覆盖异常处理) ---
    @Test
    void testExecute_Failure() {
        // 1. Arrange: 设置 DAO 抛出异常
        dao.shouldThrowException = true;
        dao.exceptionMessage = "Database connection failed";

        // 2. Act
        interactor.execute(new AssignmentsInputData());

        // 3. Assert
        assertTrue(presenter.failCalled, "Failure view should be called.");
        assertFalse(presenter.successCalled);
        assertEquals("Error loading assignments: Database connection failed", presenter.failMessage);
    }

    // --- Test 4: switchToSubmissionListView() (覆盖 getSubmissionText，包含不同状态) ---
    @Test
    void testSwitchToSubmissionListView_SubmissionsGradedAndPending() {
        // 1. Arrange
        String assignmentName = "FinalProject";
        Assignment assignment = Assignment.builder()
                .name(assignmentName)
                .supportedFileTypes(Collections.emptyList())
                .build();
        dao.assignmentToReturn = assignment;

        LocalDateTime subTime = FIXED_TIME.minusHours(1);

        // A. 已评分提交
        Submission gradedSub = new Submission(assignmentName, "Alice", subTime, "file", "data", 95.55, Submission.Status.GRADED, "ok");
        // B. 待评分提交 (Under Review)
        Submission pendingSub = new Submission(assignmentName, "Bob", subTime, "file", "data", 0.0, Submission.Status.UNDER_REVIEW, "");

        dao.submissions = Arrays.asList(gradedSub, pendingSub);

        // 2. Act
        interactor.switchToSubmissionListView(assignmentName);

        // 3. Assert
        assertTrue(presenter.switchToSubmissionListViewCalled);
        assertEquals(assignment, dao.activeAssignmentSet, "Active assignment should be set in DAO.");

        String[][] submissionTable = presenter.outputData.getSubmissions();
        assertEquals(2, submissionTable.length);

        // 验证时间格式
        String expectedTime = subTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"));

        // 验证 Alice (Graded)
        assertEquals("Alice", submissionTable[0][0]);
        assertEquals(expectedTime, submissionTable[0][1]);
        assertEquals("95.6", submissionTable[0][2], "Graded score should be formatted to 1 decimal place (95.55 -> 95.6).");

        // 验证 Bob (Pending/Under Review)
        assertEquals("Bob", submissionTable[1][0]);
        assertEquals(expectedTime, submissionTable[1][1]);
        assertEquals("pending", submissionTable[1][2], "Pending status should show 'pending'.");
    }

    // --- Test 5: 覆盖所有简单的切换方法 ---

    @Test
    void testSwitchToCreateAssignmentView() {
        interactor.switchToCreateAssignmentView();
        assertTrue(presenter.switchToCreateAssignmentViewCalled, "Presenter's method should be called.");
    }

    @Test
    void testSwitchToSubmitView() {
        interactor.switchToSubmitView();
        assertTrue(presenter.switchToSubmitViewCalled, "Presenter's method should be called.");
    }

    @Test
    void testSwitchToResubmitView() {
        interactor.switchToResubmitView();
        assertTrue(presenter.switchToResubmitViewCalled, "Presenter's method should be called.");
    }

    // ===========================================
    // === Stub Classes for Interactor Testing ===
    // ===========================================

    // Stub for AssignmentsDataAccessInterface
    static class TestAssignmentsDAO implements AssignmentsDataAccessInterface {
        List<Assignment> assignments = Collections.emptyList();
        USER_TYPE currentUserType = USER_TYPE.STUDENT;
        String courseCode = ""; // This simulates the course name returned by getCourseCode()
        boolean shouldThrowException = false;
        String exceptionMessage = "";
        Assignment assignmentToReturn = null;
        Assignment activeAssignmentSet = null;
        List<Submission> submissions = Collections.emptyList();


        @Override
        public List<Assignment> getAssignments() {
            if (shouldThrowException) {
                throw new RuntimeException(exceptionMessage);
            }
            return assignments;
        }

        @Override
        public User getCurrentUser() {
            return new User("testUser", "pass", currentUserType);
        }

        @Override
        public String getCourseCode() {
            // DAO returns the Course Code (which Interactor uses as the course name/identifier)
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
    }

    // Stub for AssignmentsOutputBoundary
    static class TestAssignmentsPresenter implements AssignmentsOutputBoundary {
        boolean successCalled = false;
        boolean failCalled = false;
        boolean switchToCreateAssignmentViewCalled = false;
        boolean switchToSubmitViewCalled = false;
        boolean switchToResubmitViewCalled = false;
        boolean switchToSubmissionListViewCalled = false;

        String failMessage;
        AssignmentsOutputData outputData;

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
    }
}