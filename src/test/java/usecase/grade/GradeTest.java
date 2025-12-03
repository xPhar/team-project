package usecase.grade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class GradeTest {
    private final GradeDataAccessInterface testDAO = new GradeDataAccessInterface() {
        public void grade(String submitter, double grade, String feedback) {
            assertEquals("gracie", submitter);
            assertEquals(80f, grade);
            assertEquals("Some generic feedback.", feedback);
        }
    };

    @Test
    void testGrade() {
        GradeOutputBoundary boundary = new GradeOutputBoundary() {
            public void prepareGradeSuccessView() {}
            public void prepareGradeFailureView(String msg) {
                fail("Did not call the correct presenter method.");
            }
        };

        GradeInputData inputData = new GradeInputData(
                "80",
                "gracie",
                "Some generic feedback."
        );

        GradeInteractor interactor = new GradeInteractor(testDAO, boundary);
        interactor.execute(inputData);
    }

    @Test
    void testGradeNotANumber() {
        GradeOutputBoundary boundary = new GradeOutputBoundary() {
            public void prepareGradeSuccessView() {
                fail("Did not call the correct presenter method.");
            }
            public void prepareGradeFailureView(String msg) {
                assertEquals("Grade must be a number!", msg);
            }
        };

        GradeInputData inputData = new GradeInputData(
                "abcde",
                "Wang",
                "Some generic feedback."
        );

        GradeInteractor interactor = new GradeInteractor(testDAO, boundary);
        interactor.execute(inputData);
    }

    @Test
    void testGradeLessThanZero() {
        GradeOutputBoundary boundary = new GradeOutputBoundary() {
            public void prepareGradeSuccessView() {
                fail("Did not call the correct presenter method.");
            }
            public void prepareGradeFailureView(String msg) {
                assertEquals("Grade must be greater than 0!", msg);
            }
        };

        GradeInputData inputData = new GradeInputData(
                "-20",
                "Yagmur",
                "Some generic feedback."
        );

        GradeInteractor interactor = new GradeInteractor(testDAO, boundary);
        interactor.execute(inputData);
    }

    @Test
    void testGradeGreaterThanMax() {
        GradeOutputBoundary boundary = new GradeOutputBoundary() {
            public void prepareGradeSuccessView() {
                fail("Did not call the correct presenter method.");
            }
            public void prepareGradeFailureView(String msg) {
                assertEquals("Grade must be less than 100.0!", msg); // Max grade hardcoded
            }
        };

        GradeInputData inputData = new GradeInputData(
                "140",
                "Yagmur",
                "Some generic feedback."
        );

        GradeInteractor interactor = new GradeInteractor(testDAO, boundary);
        interactor.execute(inputData);
    }
}
