package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a course in the coursework platform.
 * A course contains its name, course code, instructors, enrolled students, and assignments.
 */
public class Course {
    private final String name;
    private final String courseCode;
    private final List<Instructor> instructors;
    private final List<Student> students;
    private final List<Assignment> assignments;

    /**
     * Creates a new course with the specified name and course code.
     *
     * @param name the name of the course
     * @param courseCode the unique course code
     * @throws IllegalArgumentException if name or courseCode are null or empty
//     */
    public Course(String name, String courseCode) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be null or empty.");
        }
        if (courseCode == null || courseCode.isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be null or empty.");
        }

        this.name = name;
        this.courseCode = courseCode;
        this.instructors = new ArrayList<>();
        this.students = new ArrayList<>();
        this.assignments = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public List<Instructor> getInstructors() {
        return new ArrayList<>(instructors);
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public List<Assignment> getAssignments() {
        return new ArrayList<>(assignments);
    }

    public void addInstructor(Instructor instructor) {
        if (instructor != null && !instructors.contains(instructor)) {
            instructors.add(instructor);
        }
    }

    public void addStudent(Student student) {
        if (student != null && !students.contains(student)) {
            students.add(student);
        }
    }

    public void addAssignment(Assignment assignment) {
        if (assignment != null && !assignments.contains(assignment)) {
            assignments.add(assignment);
        }
    }

}
