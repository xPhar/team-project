package usecase.logged_in;


import entity.Assignment;
import entity.User;
import interface_adapter.submission_list.SubmissionTableModel;

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
    String getUsername();

    /**
     * Gets a submission table model with submissions for the given assignment.
     * @param assignment the assignment to get submissions from
     * @return the submission table model for the requested assignment
     */
    SubmissionTableModel getSubmissionTableModel(Assignment assignment);

    /**
     * Gets the assignment entity with the given name.
     * @param assignment the assignment name to search for
     * @return the assignment entity for the requested assignment
     */
    Assignment getAssignment(String assignment);

    /**
     * Sets the Session's assignment to this assignment.
     * @param assignment the assignment to make active
     */
    void setActiveAssignment(Assignment assignment);
}
