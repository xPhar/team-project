package usecase.Submission;

import java.io.File;

public interface SubmissionInputBoundary {
    void backToSubmissionList();
    void downloadFile(File saveFile);
}
