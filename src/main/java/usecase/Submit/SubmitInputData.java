package usecase.Submit;

import java.io.File;
import java.time.*;

public class SubmitInputData {
    private final LocalDateTime time;
    private final File selectedFile;

    public SubmitInputData(LocalDateTime time, File selectedFile){
        this.time = time;
        this.selectedFile = selectedFile;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public File getSelectedFile() {
        return selectedFile;
    }
}
