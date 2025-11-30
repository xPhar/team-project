package entity;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private List<Submission> submissions;

    public Student(String name, String password) {
        super(name, password, USER_TYPE.STUDENT);
        submissions = new ArrayList<>();
    }

    public Student(String name, String password, List<Submission> submissions) {
        super(name, password, USER_TYPE.STUDENT);
        this.submissions = List.copyOf(submissions == null ? List.of() : submissions);
    }

    public Student(String name, String password, List<String> courses, List<Submission> submissions) {
        super(name, password, USER_TYPE.STUDENT, courses == null ? List.of() : courses);
        this.submissions = List.copyOf(submissions == null ? List.of() : submissions);
    }

    public Submission getSubmission(String assignment) {
        for (Submission submission : submissions) {
            if (submission.getAssignment().equals(assignment)) {
                return submission;
            }
        }
        return null;
    }

    public void addSubmission(Submission submission) {
        submissions.add(submission);
    }
}
