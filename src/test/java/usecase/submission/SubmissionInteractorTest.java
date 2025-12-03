package usecase.submission;

import data_access.DataAccessException;
import entity.Submission;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SubmissionInteractorTest {
    @Test
    void testSaveFile() {
        File testFile = new File("SubmissionInteractorTest.txt");

        SubmissionDataAccessInterface testDAO = new SubmissionDataAccessInterface() {
            public void saveFile(File saveFile, String submitter) {
                assertEquals("SubmissionInteractorTest.txt", saveFile.getName());
                assertEquals("indy", submitter);
            }
            public List<Submission> getSubmissionList() {
                fail("Did not call the correct DAO method.");
                return List.of();
            }
        };

        SubmissionOutputBoundary boundary = new SubmissionOutputBoundary() {
            public void backToSubmissionListView(SubmissionOutputData data) {
                fail("Did not call the correct presenter method.");
            }
            public void prepareDownloadSuccessView(String msg) {
                assertEquals(
                        String.format("File saved to %s", testFile.getAbsolutePath()),
                        msg
                );
            }
            public void prepareDownloadFailureView(String msg) {
                fail("Did not call the correct presenter method.");
            }
        };

        SubmissionInputData inputData = new SubmissionInputData(
                false,
                testFile,
                "indy"
        );

        SubmissionInteractor interactor = new SubmissionInteractor(boundary, testDAO);
        interactor.execute(inputData);
    }

    @Test
    void testSaveFileFail() {
        File testFile = new File("SubmissionInteractorTest.txt");

        SubmissionDataAccessInterface testDAO = new SubmissionDataAccessInterface() {
            public void saveFile(File saveFile, String submitter) {
                assertEquals("SubmissionInteractorTest.txt", saveFile.getName());
                assertEquals("indy", submitter);
                throw new DataAccessException("Test failure message 1332.");
            }
            public List<Submission> getSubmissionList() {
                fail("Did not call the correct DAO method.");
                return List.of();
            }
        };

        SubmissionOutputBoundary boundary = new SubmissionOutputBoundary() {
            public void backToSubmissionListView(SubmissionOutputData data) {
                fail("Did not call the correct presenter method.");
            }
            public void prepareDownloadSuccessView(String msg) {
                fail("Did not call the correct presenter method.");
            }
            public void prepareDownloadFailureView(String msg) {
                assertEquals(
                        "Test failure message 1332.",
                        msg
                );
            }
        };

        SubmissionInputData inputData = new SubmissionInputData(
                false,
                testFile,
                "indy"
        );

        SubmissionInteractor interactor = new SubmissionInteractor(boundary, testDAO);
        interactor.execute(inputData);
    }

    @Test
    void testBackToSubmissionList() {
        SubmissionDataAccessInterface testDAO = new SubmissionDataAccessInterface() {
            public void saveFile(File saveFile, String submitter) {
                fail("Did not call the correct DAO method.");

            }
            public List<Submission> getSubmissionList() {
                ArrayList<Submission> submissionList = new ArrayList<Submission>();

                // Submission 1
                Submission.SubmissionBuilder builder = Submission.getBuilder();
                builder.submitter("Adien")
                        .submissionTime(LocalDateTime.of(2025, 11, 29, 17, 15, 22))
                        .submissionName("test.txt")
                        .submissionData("Some Data.")
                        .status(Submission.Status.GRADED)
                        .grade(65.3)
                        .feedback("Some feedback.");
                submissionList.add(builder.build());

                // Submission 2
                builder = Submission.getBuilder();
                builder.submitter("XiHang")
                        .submissionTime(LocalDateTime.of(2025, 11, 25, 20, 44, 19))
                        .submissionName("submit.py")
                        .submissionData("def f(x):")
                        .status(Submission.Status.ON_TIME);
                submissionList.add(builder.build());

                return submissionList;
            }
        };

        SubmissionOutputBoundary boundary = new SubmissionOutputBoundary() {
            public void backToSubmissionListView(SubmissionOutputData data) {
                String[][] arrayData = data.getSubmissions();
                assertEquals(2, arrayData.length);

                // Submission 1
                assertEquals("Adien", arrayData[0][0]);
                assertEquals(
                        LocalDateTime.of(2025, 11, 29, 17, 15, 22)
                                .format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")),
                        arrayData[0][1]);
                assertEquals("65.3", arrayData[0][2]);

                // Submission 2
                assertEquals("XiHang", arrayData[1][0]);
                assertEquals(
                        LocalDateTime.of(2025, 11, 25, 20, 44, 19)
                                .format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")),
                        arrayData[1][1]);
                assertEquals("pending", arrayData[1][2]);
            }
            public void prepareDownloadSuccessView(String msg) {
                fail("Did not call the correct presenter method.");
            }
            public void prepareDownloadFailureView(String msg) {
                fail("Did not call the correct presenter method.");
            }
        };

        SubmissionInputData inputData = new SubmissionInputData(
                true, null, null
        );

        SubmissionInteractor interactor = new SubmissionInteractor(boundary, testDAO);
        interactor.execute(inputData);
    }
}
