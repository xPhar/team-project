package usecase.class_average;

import java.util.List;

import entity.Submission;

/**
 * Data Access Interface for retrieving submission data
 * required by the Class Average Use Case.
 */
public interface ClassAverageUserDataAccessInterface {

    /**
     * Return all submissions (graded or not) for a specific assignment.
     * The Interactor will filter out ungraded submissions.
     *
     * @param assignmentName the assignment name
     * @return list of submissions
     */
    List<Submission> getSubmissionsFor(String assignmentName);

    /**
     * Return all assignment names for the dropdown ComboBox.
     *
     * @return list of assignment names
     */
    List<String> getAllAssignmentNames();

    /**
     * Return the username of the currently active user.
     *
     * @return username of the active user.
     */
    String getCurrentUsername();

    /**
     * Return the grade of the current user (submitter) for this assignment.
     * If the student's submission is not graded yet, return -1.
     *
     * @param assignmentName the assignment name
     * @param username the student username
     * @return numerical grade or -1
     */
    double getMyScore(String assignmentName, String username);
}
