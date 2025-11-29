package usecase.Submission;

import entity.Assignment;
import entity.Submission;
import interface_adapter.submission_list.SubmissionTableModel;

import java.io.File;
import java.util.List;

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
