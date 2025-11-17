package usecase.Submit;

import java.io.File;
import java.time.*;

public class SubmitInputData {
    private final LocalTime time;
    private final File selectedFile;

    public SubmitInputData(LocalTime time, File selectedFile){
        this.time = time;
        this.selectedFile = selectedFile;
    }

    public LocalTime getTime() {
        return time;
    }

    public File getSelectedFile() {
        return selectedFile;
    }
}
