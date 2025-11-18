package entity;

import java.util.List;

public class Course {
    private String courseName;
    private String courseCode;
    private List<Instructor> instructors;
    private List<Student> students;
    private List<Assignment> assignments;

    public Course(String courseName, String courseCode) {
        this.courseName = courseName;
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }
}
