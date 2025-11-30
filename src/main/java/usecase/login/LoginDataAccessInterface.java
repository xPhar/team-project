package usecase.login;

import entity.Assignment;
import entity.Submission;
import entity.User;

import java.util.List;

/**
 * DAO interface for the Login Use Case.
 */
public interface LoginDataAccessInterface {

    boolean existsByName(String username);

    /**
     * Returns the user with the given username.
     * @param username the username to look up
     * @return the user with the given username
     */
    User getUser(String username);

    /**
     * Optional convenience method used by some data access implementations.
     */
    default User get(String username) {
        return getUser(username);
    }

    /**
     * Saves a user if needed by the data access implementation.
     */
    default void save(User user) {
        // no-op by default
    }

    /**
     * Sets the Session's user to this user.
     * @param user the user to make active
     */
    void setActiveUser(User user);

    /**
     * Tracks the current username for lightweight implementations.
     */
    default void setCurrentUsername(String name) {
        if (getUser(name) != null) {
            setActiveUser(getUser(name));
        }
    }

    default String getCurrentUsername() {
        return "";
    }

    /**
     * Gets the assignments for the users first course.
     */
    List<Assignment> getAssignments();

    /**
     * Gets the current users submission for the given assignment.
     * @param assignment the assignment to check for a submission on.
     * @return submission associated with this user, or null if none exists
     */
    Submission getSubmission(Assignment assignment);
}
