package entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    public static final UserType INSTRUCTOR = UserType.INSTRUCTOR;
    public static final UserType STUDENT = UserType.STUDENT;

    private final UserType userType;
    private final String name;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final List<String> courses;

    public enum UserType {
        STUDENT,
        INSTRUCTOR
    }

    public User(String name, String password, String first, String last, UserType userType) {
        this(name, password, first, last, userType, new ArrayList<>());
    }

    public User(String name, String password, String first, String last, UserType userType, List<String> courses) {
        this.name = name;
        this.password = password;
        this.firstName = first;
        this.lastName = last;
        this.userType = userType;
        this.courses = new ArrayList<>(courses);
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public UserType getUserType() {
        return this.userType;
    }

    public List<String> getCourses() {
        return this.courses;
    }
}
