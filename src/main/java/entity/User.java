package entity;

import java.util.List;

public abstract class User {
    private String name;
    private String password;
    private List<Course> courses;

    User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    User(String name, String password, List<Course> courses) {
        this.name = name;
        this.password = password;
        this.courses = List.copyOf(courses);
    }

    public String getName() {
        return this.name;
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
