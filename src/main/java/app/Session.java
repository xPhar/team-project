package app;

import entity.Assignment;
import entity.Course;
import entity.User;

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

