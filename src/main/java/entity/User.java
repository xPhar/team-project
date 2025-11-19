package entity;

import java.util.List;

public class User {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private List<Course> courses;

    public User(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String username, String password, String firstName, String lastName, List<Course> courses) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.courses = List.copyOf(courses);
    }

    public String getUsername() {
        return this.username;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    public Course getCourse(String course) {
        for (Course c : this.courses) {
            if (c.getCourseCode().equals(course)) {
                return c;
            }
        }
        return null;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }
}
