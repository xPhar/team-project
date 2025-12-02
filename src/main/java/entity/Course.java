package entity;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private final String courseName;
    private final String courseCode;
    private final String latePenalty;
    private final List<String> instructors;
    private final List<String> students;
    private final List<Assignment> assignments;

    public Course(String courseName, String courseCode, String latePenalty,
                  List<String> instructors, List<String> students, List<Assignment> assignments) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.latePenalty = latePenalty;
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

    public String getLatePenalty() {
        return latePenalty;
    }

    public List<String> getInstructors() {
        return instructors;
    }

    public List<String> getStudents() {
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
        private String latePenalty;
        private final List<String> instructors;
        private final List<String> students;
        private final List<Assignment> assignments;

        CourseBuilder() {
            this.instructors = new ArrayList<>();
            this.students = new ArrayList<>();
            this.assignments = new ArrayList<>();
        }

        /**
         * Sets the name of the course.
         *
         * @param newCourseName the name of the course
         * @return the current instance of {@code CourseBuilder} for method chaining
         */
        public CourseBuilder courseName(String newCourseName) {
            courseName = newCourseName;
            return this;
        }

        /**
         * Sets the code of the course.
         *
         * @param newCourseCode the code of the course
         * @return the current instance of {@code CourseBuilder} for method chaining
         */
        public CourseBuilder courseCode(String newCourseCode) {
            courseCode = newCourseCode;
            return this;
        }

        /**
         * Sets the late penalty information for the course.
         *
         * @param newLatePenalty the late penalty details, typically describing the rules or deductions
         *         for late submissions
         * @return the current instance of {@code CourseBuilder} for method chaining
         */
        public CourseBuilder latePenalty(String newLatePenalty) {
            latePenalty = newLatePenalty;
            return this;
        }

        /**
         * Adds an instructor to the course being built.
         *
         * @param instructor the instructor to be added to the course
         * @return the current instance of {@code CourseBuilder} for method chaining
         */
        public CourseBuilder addInstructor(String instructor) {
            instructors.add(instructor);
            return this;
        }

        /**
         * Adds a student to the course being built.
         *
         * @param student the student to be added to the course
         * @return the current instance of {@code CourseBuilder} for method chaining
         */
        public CourseBuilder addStudent(String student) {
            students.add(student);
            return this;
        }

        /**
         * Adds an assignment to the course being built.
         *
         * @param assignment the assignment to be added to the course
         * @return the current instance of {@code CourseBuilder} for method chaining
         */
        public CourseBuilder addAssignment(Assignment assignment) {
            assignments.add(assignment);
            return this;
        }

        /**
         * Constructs and returns a new {@link Course} instance with the attributes that have been
         * configured using the {@code CourseBuilder}.
         *
         * @return a new instance of {@link Course} initialized with the builder's configured values
         */
        public Course build() {
            return new Course(courseName, courseCode, latePenalty, instructors, students, assignments);
        }
    }
}

