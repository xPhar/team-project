package usecase.SubmissionList;

import entity.Submission;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class SubmissionListInteractorTest {
    private final SubmissionListDataAccessInterface testDAO =
            new SubmissionListDataAccessInterface() {
                @Override
                public Submission getSubmissionForSubmissionView(String submitter) {
                    if (submitter.equals("indy")) {
                        return new Submission(
                                "indy",
                                LocalDateTime.of(2025,11,29, 16, 47, 0),
                                "submission.java",
                                "123456\naabbcc",
                                86.37,
                                Submission.GRADED,
                                "Good job!"
                        );
                    }
                    else if (submitter.equals("indy2")) {
                        return new Submission(
                                "indy2",
                                LocalDateTime.of(2025,11,29, 16, 47, 0),
                                "submission.java",
                                "123456\naabbcc",
                                -1,
                                Submission.ON_TIME,
                                null
                        );
                    }
                    else {
                        fail("Did not pass correct submitter to the DAO.");
                        return null;
                    }
                }
            };

    @Test
    void testBack() {
        SubmissionListOutputBoundary boundary = new SubmissionListOutputBoundary() {
            public void prepareSubmissionView (SubmissionListOutputData data){
                fail("Did not call the correct presenter method.");
            }
            public void goToAssignmentView () {}
        };

        SubmissionListInputData testInputData = new SubmissionListInputData(true, null, null);

        SubmissionListInteractor interactor = new SubmissionListInteractor(boundary, testDAO);
        interactor.execute(testInputData);
    }

    @Test
    void testSelectSubmission() {
        final SubmissionListOutputBoundary boundary = new SubmissionListOutputBoundary() {
            public void prepareSubmissionView (SubmissionListOutputData data){
                assertEquals("Test Assignment", data.getAssignmentName());
                assertEquals("indy", data.getSubmitter());
                assertEquals(Submission.GRADED.toString(), data.getStatus());
                assertEquals(
                        LocalDateTime.of(2025,11,29, 16, 47, 0)
                                .format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")),
                        data.getSubmissionDate()
                );
                assertEquals("submission.java", data.getSubmissionName());
                assertEquals("86.4", data.getGrade());
                assertEquals("Good job!", data.getFeedback());
                assertEquals("100", data.getMaxGrade()); // Hardcoded
            }
            public void goToAssignmentView () {
                fail("Did not call the correct presenter method.");
            }
        };

        SubmissionListInputData testInputData = new SubmissionListInputData(false,
                "indy", "Test Assignment");

        SubmissionListInteractor interactor = new SubmissionListInteractor(boundary, testDAO);
        interactor.execute(testInputData);
    }

    @Test
    void testSelectSubmissionNoGrade() {
        final SubmissionListOutputBoundary boundary = new SubmissionListOutputBoundary() {
            public void prepareSubmissionView (SubmissionListOutputData data){
                assertEquals("Test Assignment", data.getAssignmentName());
                assertEquals("indy2", data.getSubmitter());
                assertEquals(Submission.ON_TIME.toString(), data.getStatus());
                assertEquals(
                        LocalDateTime.of(2025,11,29, 16, 47, 0)
                                .format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")),
                        data.getSubmissionDate()
                );
                assertEquals("submission.java", data.getSubmissionName());
                assertEquals("", data.getGrade());
                assertEquals("", data.getFeedback());
                assertEquals("100", data.getMaxGrade()); // Hardcoded
            }
            public void goToAssignmentView () {
                fail("Did not call the correct presenter method.");
            }
        };

        SubmissionListInputData testInputData = new SubmissionListInputData(false,
                "indy2", "Test Assignment");

        SubmissionListInteractor interactor = new SubmissionListInteractor(boundary, testDAO);
        interactor.execute(testInputData);
    }
}
