package usecase.Resubmit;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public interface ResubmitUserDataAccessInterface {
    /*
     * Get due date of the current assignment
     */
    LocalDateTime getAssignmentDueDate();
}
