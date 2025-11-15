package usecase.Submit;

import java.io.File;
import java.io.IOException;

public interface SubmitUserDataAccessInterface {

    void submit(File studentFile, String studentName, String assignmentName, String courseCode) throws IOException;
}
