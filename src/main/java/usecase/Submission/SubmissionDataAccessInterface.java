package usecase.Submission;

import entity.Submission;

/**
 * Data access interface for submission view.
 */
public interface SubmissionDataAccessInterface {

    /**
     * Returns a specific submission based on the assignment and the submitter's name.
     * @param assignmentName the name of the assignment
     * @param submitter the name of the assignment
     * @return the submission made by the submitter for the assignment
     */
    Submission getSubmission(String assignmentName, String submitter);
}
