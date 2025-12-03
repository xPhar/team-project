package usecase.submission;

import java.io.File;
import java.util.List;

import entity.Submission;

public interface SubmissionDataAccessInterface {
    /**
     * Get the file for the submitter and save to disk.
     * @param saveFile the file to save
     * @param submitter the submitter of the submission
     */
    void saveFile(File saveFile, String submitter);

    /**
     * Gets a submission table model with submissions for the current assignment.
     * @return the submission table model for the current assignment
     */
    List<Submission> getSubmissionList();
}
