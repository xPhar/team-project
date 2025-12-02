package usecase.ClassAverage;

import entity.Submission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ClassAverageInteractorTest {

    private TestDAO dao;
    private TestPresenter presenter;
    private ClassAverageInteractor interactor;

    @BeforeEach
    void setup() {
        dao = new TestDAO();
        presenter = new TestPresenter();
        interactor = new ClassAverageInteractor(dao, presenter);
    }

    @Test
    void testBackToLoggedIn() {
        ClassAverageInputData input = new ClassAverageInputData(true, null);
        interactor.execute(input);
        assertTrue(presenter.backCalled);
    }

    @Test
    void testAssignmentNull() {
        dao.assignments = Arrays.asList("A1", "A2");
        ClassAverageInputData input = new ClassAverageInputData(false, null);
        interactor.execute(input);
        assertTrue(presenter.successCalled);
        assertEquals(2, presenter.data.getAssignmentNames().size());
        assertEquals(0, presenter.data.getStudentCount());
    }

    @Test
    void testAssignmentDefaultString() {
        dao.assignments = Arrays.asList("HW");
        ClassAverageInputData input = new ClassAverageInputData(false, "Assignment");
        interactor.execute(input);
        assertTrue(presenter.successCalled);
        assertEquals(1, presenter.data.getAssignmentNames().size());
        assertEquals(0, presenter.data.getStudentCount());
    }

    @Test
    void testNotGradedYet() {
        dao.submissions = Arrays.asList(
                new Submission(
                        "bob",
                        LocalDateTime.now(),
                        "hw1",
                        "data",
                        0.0,
                        Submission.Status.UNDER_REVIEW,
                        "none"
                )
        );

        ClassAverageInputData input = new ClassAverageInputData(false, "HW1");
        interactor.execute(input);

        assertTrue(presenter.failCalled);
        assertEquals("Assignment not graded yet!", presenter.failMessage);
    }

    @Test
    void testNormalCalculation() {
        dao.currentUser = "me";
        dao.assignments = Arrays.asList("HW1");
        dao.myScore = 80;

        dao.submissions = Arrays.asList(
                new Submission("s1", LocalDateTime.now(), "hw1", "file", 60, Submission.Status.GRADED, "ok"),
                new Submission("s2", LocalDateTime.now(), "hw1", "file", 80, Submission.Status.GRADED, "ok"),
                new Submission("s3", LocalDateTime.now(), "hw1", "file", 90, Submission.Status.GRADED, "ok")
        );

        ClassAverageInputData input = new ClassAverageInputData(false, "HW1");
        interactor.execute(input);
        assertTrue(presenter.successCalled);
        ClassAverageOutputData out = presenter.data;

        assertEquals(3, out.getStudentCount());
        assertEquals(76.6666, out.getMean(), 0.01);
        assertEquals(80, out.getMedian(), 0.01);
        assertEquals(12.47, out.getStdDev(), 0.1);
        assertEquals(1, out.getHistogram().get("51-60"));
        assertEquals(1, out.getHistogram().get("71-80"));
        assertEquals(1, out.getHistogram().get("81-90"));
    }

    @Test
    void testAllGetters() {

        List<String> assignments = Arrays.asList("A1", "A2");
        Map<String, Integer> histogram = new LinkedHashMap<>();
        histogram.put("0-10", 2);
        histogram.put("11-20", 1);

        ClassAverageOutputData data = new ClassAverageOutputData(
                assignments,
                3,
                75.5,
                80.0,
                10.0,
                90.0,
                histogram,
                "A1"
        );

        assertEquals(assignments, data.getAssignmentNames());
        assertEquals(3, data.getStudentCount());
        assertEquals(75.5, data.getMean());
        assertEquals(80.0, data.getMedian());
        assertEquals(10.0, data.getStdDev());
        assertEquals(90.0, data.getMyScore());
        assertEquals(histogram, data.getHistogram());
        assertEquals("A1", data.getAssignmentName());
    }

    @Test
    void testNormalCalculation_AllHistogramBuckets() {

        dao.currentUser = "me";
        dao.assignments = Arrays.asList("HW1");
        dao.myScore = 80;

        dao.submissions = Arrays.asList(
                new Submission("s0", LocalDateTime.now(), "hw1", "file", 5, Submission.Status.GRADED, "ok"),
                new Submission("s1", LocalDateTime.now(), "hw1", "file", 15, Submission.Status.GRADED, "ok"),
                new Submission("s2", LocalDateTime.now(), "hw1", "file", 25, Submission.Status.GRADED, "ok"),
                new Submission("s3", LocalDateTime.now(), "hw1", "file", 35, Submission.Status.GRADED, "ok"),
                new Submission("s4", LocalDateTime.now(), "hw1", "file", 45, Submission.Status.GRADED, "ok"),
                new Submission("s5", LocalDateTime.now(), "hw1", "file", 55, Submission.Status.GRADED, "ok"),
                new Submission("s6", LocalDateTime.now(), "hw1", "file", 65, Submission.Status.GRADED, "ok"),
                new Submission("s7", LocalDateTime.now(), "hw1", "file", 75, Submission.Status.GRADED, "ok"),
                new Submission("s8", LocalDateTime.now(), "hw1", "file", 85, Submission.Status.GRADED, "ok"),
                new Submission("s9", LocalDateTime.now(), "hw1", "file", 95, Submission.Status.GRADED, "ok")
        );

        ClassAverageInputData input = new ClassAverageInputData(false, "HW1");
        interactor.execute(input);
        assertTrue(presenter.successCalled);
        Map<String, Integer> h = presenter.data.getHistogram();
        for (int count : h.values()) {
            assertEquals(1, count);
        }
        assertEquals(10, presenter.data.getStudentCount());
    }

    static class TestDAO implements ClassAverageUserDataAccessInterface {

        List<Submission> submissions = new ArrayList<>();
        List<String> assignments = new ArrayList<>();
        String currentUser = "student";
        double myScore = 0;

        @Override
        public List<Submission> getSubmissionsFor(String assignmentName) {
            return submissions;
        }

        @Override
        public List<String> getAllAssignmentNames() {
            return assignments;
        }

        @Override
        public double getMyScore(String assignmentName, String username) {
            return myScore;
        }

        @Override
        public String getCurrentUsername() {
            return currentUser;
        }
    }

    static class TestPresenter implements ClassAverageOutputBoundary {

        boolean backCalled = false;
        boolean successCalled = false;
        boolean failCalled = false;

        String failMessage;
        ClassAverageOutputData data;

        @Override
        public void backToLoggedInView() {
            backCalled = true;
        }

        @Override
        public void prepareSuccessView(ClassAverageOutputData data) {
            successCalled = true;
            this.data = data;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            failCalled = true;
            this.failMessage = errorMessage;
        }
    }
}
