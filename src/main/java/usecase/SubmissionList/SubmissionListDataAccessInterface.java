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
}
