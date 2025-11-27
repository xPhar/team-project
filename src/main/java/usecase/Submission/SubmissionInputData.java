package usecase.Submission;

import java.io.File;

public class SubmissionInputData {
    private final File saveFile;
    private final String submitter;

    public SubmissionInputData(
            File saveFile,
            String submitter) {
        this.saveFile = saveFile;
        this.submitter = submitter;
    }

    public String getSubmitter() {
        return submitter;
    }

    public File getSaveFile() {
        return saveFile;
    }
}
