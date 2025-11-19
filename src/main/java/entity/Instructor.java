package entity;

import java.util.List;

public class Instructor extends User {
    Instructor(String name, String password) {
        super(name, password, "please", "remove");
    }

    Instructor(String name, String password, List<Course> courses) {
        super(name, password, "remove", "this", courses);
    }
}
