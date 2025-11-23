package usecase.Submit;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public interface SubmitUserDataAccessInterface {
    /*
    Send this file to Database, signature may change
     */
    void submit(File studentFile) throws IOException;

    /*
     * Get due date of the current assignment
     */
    LocalDateTime getAssignmentDueDate();
}
