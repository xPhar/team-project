package entity;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseName;
    private String courseCode;
    private List<String> instructors;
    private List<String> students;
    private List<String> assignments;

    public Course(String courseName, String courseCode,  List<String> instructors,
                  List<String> students, List<String> assignments) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.instructors = instructors;
        this.students = students;
        this.assignments = assignments;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public List<String> getInstructors() {
        return instructors;
    }

    public List<String> getStudents() {
        return students;
    }

    public List<String> getAssignments() {
        return assignments;
    }

    public static CourseBuilder getBuilder() {
        return new CourseBuilder();
    }

    public static class CourseBuilder {
        private String courseName;
        private String courseCode;
        private List<String> instructors;
        private List<String> students;
        private List<String> assignments;

        CourseBuilder() {
            this.instructors = new ArrayList<>();
            this.students = new ArrayList<>();
            this.assignments = new ArrayList<>();
        }

        public CourseBuilder courseName(String courseName) {
            this.courseName = courseName;
            return this;
        }

        public CourseBuilder courseCode(String courseCode) {
            this.courseCode = courseCode;
            return this;
        }

        public CourseBuilder addInstructor(String instructor) {
            this.instructors.add(instructor);
            return this;
        }

        public CourseBuilder addStudent(String student) {
            this.students.add(student);
            return this;
        }

        public CourseBuilder addAssignment(String assignment) {
            this.assignments.add(assignment);
            return this;
        }

        public Course build() {
            return new  Course(courseName, courseCode, instructors, students, assignments);
        }
    }
}
