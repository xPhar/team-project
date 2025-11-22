package entity;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseName;
    private String courseCode;
    private List<User> instructors;
    private List<User> students;
    private List<Assignment> assignments;

    public Course(String courseName, String courseCode,  List<User> instructors,
                  List<User> students, List<Assignment> assignments) {
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

    public List<User> getInstructors() {
        return instructors;
    }

    public List<User> getStudents() {
        return students;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public static CourseBuilder getBuilder() {
        return new CourseBuilder();
    }

    public static class CourseBuilder {
        private String courseName;
        private String courseCode;
        private List<User> instructors;
        private List<User> students;
        private List<Assignment> assignments;

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

        public CourseBuilder addInstructor(User instructor) {
            this.instructors.add(instructor);
            return this;
        }

        public CourseBuilder addStudent(User student) {
            this.students.add(student);
            return this;
        }

        public CourseBuilder addAssignment(Assignment assignment) {
            this.assignments.add(assignment);
            return this;
        }

        public Course build() {
            return new  Course(courseName, courseCode, instructors, students, assignments);
        }
    }
}
