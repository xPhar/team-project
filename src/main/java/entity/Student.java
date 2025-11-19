package entity;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private List<Submission> submissions;

    Student(String name, String password) {
        super(name, password, "remove", "this");
        this.submissions = new ArrayList<>();
    }

    Student(String name, String password, List<Submission> submissions) {
        super(name, password, "remove", "this");
        this.submissions = List.copyOf(submissions);
    }

    Student(String name, String password, List<Course> courses, List<Submission> submissions) {
        super(name, password, "remove", "this", courses);
        this.submissions = List.copyOf(submissions);
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
