package entity;

import java.util.List;

public class Instructor extends User {
    public Instructor(String name, String password) {
        super(name, password, USER_TYPE.INSTRUCTOR);
    }

    public Instructor(String name, String password, List<Course> courses) {
        super(name, password, USER_TYPE.INSTRUCTOR,
                courses == null ? List.of() : courses.stream().map(Course::getCourseName).toList());
    }
}
