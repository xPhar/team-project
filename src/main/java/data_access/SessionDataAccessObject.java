package data_access;

import entity.*;

public class SessionDataAccessObject {
    final Session session;

    public SessionDataAccessObject() {
        this.session = Session.getInstance();
    }

    public User getUser() {
        return this.session.getUser();
    }

    public void setUser(User user) {
        this.session.setUser(user);
    }

    public Course getCourse() {
        return this.session.getCourse();
    }

    public void setCourse(Course course) {
        this.session.setCourse(course);
    }

    public Assignment getAssignment() {
        return this.session.getAssignment();
    }

    public void setAssignment(Assignment assignment) {
        this.session.setAssignment(assignment);
    }
}
