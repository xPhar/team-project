package usecase.Submit;

import java.io.File;
import java.io.IOException;

public interface SubmitUserDataAccessInterface {
    /*
    Send this file to Database, signature may change
     */
    void submit(File studentFile, String studentName, String assignmentName, String courseCode) throws IOException;
}
