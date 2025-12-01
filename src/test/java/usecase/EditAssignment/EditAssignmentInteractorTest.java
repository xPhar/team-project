package usecase.EditAssignment;

import entity.Assignment;
import usecase.Assignments.AssignmentsInputBoundary;
import usecase.Assignments.AssignmentsInputData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 单元测试 EditAssignmentInteractor，覆盖编辑成功和数据访问失败的路径。
 */
class EditAssignmentInteractorTest {

    private TestEditAssignmentDAO dataAccess;
    private TestEditAssignmentPresenter presenter;
    private TestAssignmentsInputBoundary assignmentsBoundary;
    private EditAssignmentInteractor interactor;

    // --- Setup ---

    @BeforeEach
    void setup() {
        dataAccess = new TestEditAssignmentDAO();
        presenter = new TestEditAssignmentPresenter();
        assignmentsBoundary = new TestAssignmentsInputBoundary();
        interactor = new EditAssignmentInteractor(dataAccess, presenter, assignmentsBoundary);
    }

    // --- Test 1: 成功路径 (Success Path) ---
    @Test
    void testExecute_Success() {
        // 1. Arrange: 准备输入数据
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

        // 2. Act
        interactor.execute(inputData);

        // 3. Assert (验证执行流程和数据更新)

        // 验证 Success View 被调用
        assertTrue(presenter.successCalled, "Presenter's success view should be called.");
        assertFalse(presenter.failCalled, "Failure view should NOT be called.");

        // 验证 AssignmentsInputBoundary 被调用以刷新列表
        assertTrue(assignmentsBoundary.executeCalled, "Assignments view refresh should be triggered.");

        // 验证 DAO update 方法被调用
        assertTrue(dataAccess.updateCalled, "DAO update method should be called.");

        // 验证传递给 DAO 的参数是否正确
        assertEquals(courseCode, dataAccess.courseCodePassed);
        assertEquals(originalName, dataAccess.originalNamePassed);

        // 验证更新后的 Assignment 数据的正确性
        Assignment updatedAssignment = dataAccess.updatedAssignment;
        assertNotNull(updatedAssignment, "Updated assignment object should not be null.");
        assertEquals(newName, updatedAssignment.getName());
        assertEquals(newDueDate, updatedAssignment.getDueDate());
        assertEquals(newFileTypes, updatedAssignment.getSupportedFileTypes());
        assertEquals("Updated description for the project.", updatedAssignment.getDescription());
    }

    // --- Test 2: 失败路径 (Exception Failure: Data Access Error) ---
    @Test
    void testExecute_Failure_DataAccessError() {
        // 1. Arrange: 设置 DAO 抛出异常
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

        // 2. Act
        interactor.execute(inputData);

        // 3. Assert (验证 Failure View 捕获异常消息)

        // 验证 Failure View 被调用
        assertTrue(presenter.failCalled, "Presenter's failure view should be called due to exception.");
        assertEquals("Assignment not found in database.", presenter.failMessage);

        // 验证 Success 流程未被调用
        assertFalse(presenter.successCalled);
        assertFalse(assignmentsBoundary.executeCalled, "Assignments refresh should NOT be called.");
    }


    // =================================================================================================
    // === Stub Classes (Fake Implementations) for Dependency Injection ==================================
    // =================================================================================================

    // Stub for AssignmentsInputBoundary (验证 Interactor 是否触发了刷新)
    static class TestAssignmentsInputBoundary implements AssignmentsInputBoundary {
        public boolean executeCalled = false;
        // 必须实现接口中的所有方法
        @Override
        public void execute(AssignmentsInputData inputData) {
            executeCalled = true;
        }

        @Override
        public void switchToCreateAssignmentView() {}
        @Override
        public void switchToSubmitView() {}
        @Override
        public void switchToResubmitView() {}
        @Override
        public void switchToSubmissionListView(String assignmentName) {}
    }

    // Stub for EditAssignmentDataAccessInterface (验证数据是否被保存或抛出异常)
    static class TestEditAssignmentDAO implements EditAssignmentDataAccessInterface {
        public boolean updateCalled = false;
        public String courseCodePassed = null;
        public String originalNamePassed = null;
        public Assignment updatedAssignment = null;

        public boolean shouldThrowException = false;
        public String exceptionMessage = "";

        @Override
        // **注意：由于 EditAssignmentInteractor 没有 try-catch 块包裹 DAO 调用，
        // 并且 EditAssignmentDataAccessInterface 没有声明 throws Exception，
        // 我们必须使用 RuntimeException 来模拟失败路径。
        public void updateAssignment(String courseCode, String originalAssignmentName, Assignment assignment) {
            updateCalled = true;
            courseCodePassed = courseCode;
            originalNamePassed = originalAssignmentName;
            updatedAssignment = assignment;

            if (shouldThrowException) {
                // 抛出非受检异常，模拟数据库或找不到数据的错误
                throw new RuntimeException(exceptionMessage);
            }
        }
    }

    // Stub for EditAssignmentOutputBoundary (验证正确 View 是否被调用)
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
