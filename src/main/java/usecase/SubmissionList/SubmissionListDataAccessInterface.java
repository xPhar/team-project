package usecase.SubmissionList;

import entity.Submission;

import java.util.List;

/**
 * Data access interface for submission list view.
 */
public interface SubmissionListDataAccessInterface {
    /**
     * Returns a specific submission based on the submitter's name.
     * @param submitter the name of the assignment
     * @return the submission made by the submitter
     */
    Submission getSubmissionForSubmissionView(String submitter);
}
