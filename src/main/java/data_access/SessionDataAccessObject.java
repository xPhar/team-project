package data_access;

import entity.Assignment;
import entity.Course;
import entity.Session;
import entity.User;

public class SessionDataAccessObject {
    private final Session session;

    public SessionDataAccessObject() {
        this.session = Session.getInstance();
    }

    public void resetSession() {
        setUser(null);
        setCourse(null);
        setAssignment(null);
    }

    /**
     * Retrieves the currently active user from the session.
     *
     * @return the active User object retrieved from the session.
     */
    public User getUser() {
        return this.session.getUser();
    }

    /**
     * Sets the active user for the session.
     *
     * @param user the User object to set as the active user in the session
     */
    public void setUser(User user) {
        this.session.setUser(user);
    }

    /**
     * Retrieves the currently active course from the session.
     *
     * @return the active Course object retrieved from the session.
     */
    public Course getCourse() {
        return this.session.getCourse();
    }

    /**
     * Sets the active course for the session.
     *
     * @param course the Course object to set as the active course in the session
     */
    public void setCourse(Course course) {
        this.session.setCourse(course);
    }

    /**
     * Retrieves the currently active assignment from the session.
     *
     * @return the active Assignment object retrieved from the session.
     */
    public Assignment getAssignment() {
        return this.session.getAssignment();
    }

    /**
     * Sets the active assignment for the session.
     *
     * @param assignment the Assignment object to set as the active assignment in the session
     */
    public void setAssignment(Assignment assignment) {
        this.session.setAssignment(assignment);
    }
}
