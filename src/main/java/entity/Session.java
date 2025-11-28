package entity;

/*
Storing runtime data that will be passed between interactor (maybe DAO)
Can be implemented as pattern Singleton
 */
public final class Session {
    private static Session session;
    private User user;
    private Course course;
    private Assignment assignment;

    private Session() {
        user = null;
        course = null;
        assignment = null;
    }

    /**
     * Provides a globally accessible instance of the {@code Session} class, ensuring that only
     * one instance exists throughout the application's lifecycle.
     *
     * <p></p>
     * This method follows the Singleton design pattern. If the instance does not already exist,
     * it initializes a new instance. If an instance already exists, it returns the existing instance.
     *
     * @return the single instance of the {@code Session} class
     */
    public static Session getInstance() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    public User getUser() {
        return user;
    }

    // -----------------------
    // User
    // -----------------------
    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    // -----------------------
    // Course
    // -----------------------
    public void setCourse(Course course) {
        this.course = course;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    // -----------------------
    // Assignment
    // -----------------------
    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }
}
