package usecase.submission;

import java.io.File;

public class SubmissionInputData {
    private final boolean back;
    private final File saveFile;
    private final String submitter;

    public SubmissionInputData(
            boolean back,
            File saveFile,
            String submitter) {
        this.back = back;
        this.saveFile = saveFile;
        this.submitter = submitter;
    }

    public String getSubmitter() {
        return submitter;
    }

    public File getSaveFile() {
        return saveFile;
    }

    public boolean isBack() {
        return back;
    }
}
