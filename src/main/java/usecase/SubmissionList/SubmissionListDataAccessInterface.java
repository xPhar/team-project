package usecase.SubmissionList;

import entity.Submission;

import java.util.List;

/**
 * Data access interface for submission list view.
 */
public interface SubmissionListDataAccessInterface {
    /**
     * Get a list of submissions for an assignment.
     * @param assignmentName the name of the assignment
     * @return a list of submissions for that assignment
     */
    List<Submission> getSubmissionList(String assignmentName);

    /**
     * Returns a specific submission based on the assignment and the submitter's name.
     * @param assignmentName the name of the assignment
     * @param submitter the name of the assignment
     * @return the submission made by the submitter for the assignment
     */
    Submission getSubmission(String assignmentName, String submitter);
}
