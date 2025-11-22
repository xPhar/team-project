package usecase.login;

import entity.User;

/**
 * DAO interface for the Login Use Case.
 */
public interface LoginDataAccessInterface {

    /**
     * Checks if the given username exists.
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByName(String username);

    /**
     * Returns the user with the given username.
     * @param username the username to look up
     * @return the user with the given username
     */
    User getUser(String username);

    /**
     * Sets the Session's user to this user.
     * @param user the user to make active
     */
    void setActiveUser(User user);
}
