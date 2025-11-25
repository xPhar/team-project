package entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private USER_TYPE userType;
    private String name;
    private String password;
    private String firstName;
    private String lastName;
    private List<String> courses;

    public enum USER_TYPE {
        STUDENT,
        INSTRUCTOR
    }

    public static final USER_TYPE STUDENT = USER_TYPE.STUDENT;
    public static final USER_TYPE INSTRUCTOR = USER_TYPE.INSTRUCTOR;

    public User(String name, String password, String first, String last, USER_TYPE userType) {
        this(name, password, first, last, userType, new ArrayList<>());
    }

    public User(String name, String password, String first, String last, USER_TYPE userType, List<String> courses) {
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

    public USER_TYPE getUserType() {
        return this.userType;
    }

    public List<String> getCourses() {
        return this.courses;
    }
}
