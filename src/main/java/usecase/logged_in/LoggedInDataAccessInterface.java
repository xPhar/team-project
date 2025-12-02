package usecase.logged_in;


import entity.Assignment;
import entity.Submission;
import entity.User;
import interface_adapter.submission_list.SubmissionTableModel;

import java.util.List;

/**
 * DAO interface for the Logged In Use Case.
 */
public interface LoggedInDataAccessInterface {
    /**
     * Resets all information related to the current session
     */
    void resetSession();

    /**
     * Gets the username of the current user.
     * @return the username for the current user
     */
    String getCurrentUsername();

    /**
     * Gets the user type of the current user.
     * @return the user type for the current user
     */
    User.UserType getUserType();

    /**
     * Gets a list of submissions for the given assignment.
     * @param assignment the assignment to get submissions from
     * @return the list of submissions for the requested assignment
     */
    List<Submission> getSubmissionList(Assignment assignment);

    /**
     * Gets the assignment entity with the given name.
     * @param assignment the assignment name to search for
     * @return the assignment entity for the requested assignment
     */
    Assignment getAssignment(String assignment);

    /**
     * Gets the assignments for the current user in the current course.
     * @return a list containing the names of all assignments relevant to the current user and course.
     */
    List<String> getAllAssignmentNames();

    /**
     * Gets whether the current user has a submission for the given assignment.
     * Assumes the current user is a student.
     * @param assignment the assignment to look for a submission.
     * @return true if the user has a submission, otherwise false
     */
    boolean userHasSubmitted(Assignment assignment);

    /**
     * Sets the Session's assignment to this assignment.
     * @param assignment the assignment to make active
     */
    void setActiveAssignment(Assignment assignment);
}
