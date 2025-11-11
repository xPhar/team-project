package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple entity representing a user. Users have a username, a password, and accessible courses.
 */
public class User {

    private final String name;
    private final String password;
    private final List<Course> courses;

    /**
     * Creates a new user with the given non-empty name, non-empty password,  and a list of courses they have access to.
     * @param name the username
     * @param password the password
     * @param courses the fixed list of courses the user can access
     * @throws IllegalArgumentException if name and password are empty or null, or courses are null
     */
    public User(String name, String password, List<Course> courses) {
        if ("".equals(name) || name == null) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if ("".equals(password) || password == null) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (courses == null) {
            throw new IllegalArgumentException("Courses list cannot be null.");
        }
        this.name = name;
        this.password = password;
        this.courses = new ArrayList<>(courses);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean validatePassword(String inputPassword) {
        if (inputPassword == null) {
            return false;
        }
        return this.password.equals(inputPassword);
    }

    public Course getCourse(String courseName) {
        for (Course c : courses) {
            if (c.getName().equalsIgnoreCase(courseName)) {
                return c;
            }
        }
        return null;
    }

}
