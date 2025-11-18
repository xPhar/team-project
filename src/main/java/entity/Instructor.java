package entity;

import java.util.List;

public class Instructor extends User {
    public Instructor(String name, String password) {
        super(name, password);
    }

    public Instructor(String name, String password, List<Course> courses) {
        super(name, password, courses);
    }
}