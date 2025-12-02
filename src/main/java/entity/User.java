package entity;

import java.util.ArrayList;
import java.util.List;

public class User {

    public enum USER_TYPE {
        STUDENT,
        INSTRUCTOR
    }

    public static final USER_TYPE INSTRUCTOR = USER_TYPE.INSTRUCTOR;
    public static final USER_TYPE STUDENT = USER_TYPE.STUDENT;

    private final USER_TYPE USERTYPE;
    private final String name;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final List<String> courses;

    public User(String name, String password, String first, String last, USER_TYPE USERTYPE) {
        this(name, password, first, last, USERTYPE, new ArrayList<>());
    }

    public User(String name, String password, String first, String last, USER_TYPE USERTYPE, List<String> courses) {
        this.name = name;
        this.password = password;
        this.firstName = first;
        this.lastName = last;
        this.USERTYPE = USERTYPE;
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
        return this.USERTYPE;
    }

    public List<String> getCourses() {
        return this.courses;
    }
}
