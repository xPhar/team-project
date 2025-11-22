package entity;

/*
Storing runtime data that will be passed between interactor (maybe DAO)
 */
public class Session {
    private User user;
    private Course course;
    private Assignment assignment;

    // -----------------------
    // User
    // -----------------------
    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    // -----------------------
    // Course
    // -----------------------
    public void setCourse(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    // -----------------------
    // Assignment
    // -----------------------
    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Assignment getAssignment() {
        return assignment;
    }
}

