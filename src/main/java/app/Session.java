package app;

import entity.*;

/*
Storing runtime data that will be passed between interactor (maybe DAO)
Can be implemented as pattern Singleton
 */
public class Session {

    private User user;
    private Course course;
    private Assignment assignment;
    private static Session session = null;

    private Session() {
        //user = new Student("This is a test Name", "This is a test pwd");
        //course = new Course("Course Name", "TEST101");
        //assignment = new AssignmentBuilder()
        //        .dueDate(LocalDateTime.MAX)
        //        .name("This is a test Name")
        //        // May or may not add other default properties
        //        .build();
        user = null;
        course = null;
        assignment = null;
    }

    public static Session getInstance() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

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

    // -----------------------
    // Utility (optional)
    // -----------------------
    public void resetCourseSelection() {
        this.course = null;
        this.assignment = null;
    }

    public void resetAssignment() {
        this.assignment = null;
    }

    public void resetAll() {
        this.user = null;
        this.course = null;
        this.assignment = null;
    }
}
