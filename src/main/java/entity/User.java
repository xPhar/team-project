package entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String password;
    private USER_TYPE userType;
    private List<String> courses;

    public enum USER_TYPE {
        STUDENT,
        INSTRUCTOR
    }

    public User(String name, String password, USER_TYPE userType) {
        this(name, password, userType, new ArrayList<>());
    }

    public User(String name, String password, USER_TYPE userType, List<String> courses) {
        this.name = name;
        this.password = password;
        this.userType =  userType;
        this.courses = new ArrayList<>(courses);
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public USER_TYPE getUserType() {
        return this.userType;
    }

    public List<String> getCourses() {
        return this.courses;
    }
}
