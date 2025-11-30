package data_access;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Assignment;
import entity.Submission;
import entity.User;
import usecase.Assignments.AssignmentsDataAccessInterface;
import usecase.CreateAssignment.CreateAssignmentDataAccessInterface;
import usecase.EditAssignment.EditAssignmentDataAccessInterface;
import usecase.Grade.GradeDataAccessInterface;
import usecase.Submission.SubmissionDataAccessInterface;
import usecase.SubmissionList.SubmissionListDataAccessInterface;

public class TestDAO implements
        SubmissionListDataAccessInterface,
        GradeDataAccessInterface,
        SubmissionDataAccessInterface,
        AssignmentsDataAccessInterface,
        CreateAssignmentDataAccessInterface,
        EditAssignmentDataAccessInterface {
    public static final int DAYS = 7;
    public static final int INTERVAL = 3;
    public static final double HOUR_IN_A_DAY = 24.0;
    public static final String PDF = "pdf";
    public static final String ZIP = "zip";
    public static final String JAVA = "java";
    public static final int DAYS_IN_2_WEEKS = 14;
    public static final int TWO_DAYS = 2;
    public static final double HALF_OF_A_DAY = 12.0;
    public static final String PY = "py";
    public static final float TEST_GRADE1 = 20f;
    public static final int TEST_LIST_LENGTH = 50;
    public static final double RANDOM_THRESHOLD = 0.8;
    public static final int GRADE_MULTIPLIER = 1000;
    public static final double GRADE_DIVISOR = 10.0;
    private final Map<String, List<Assignment>> assignmentsByCourse = new HashMap<>();

    public TestDAO() {
        // Initialize with some sample assignments for testing
        final List<Assignment> csc207Assignments = new ArrayList<>();

        final Assignment assignment1 = Assignment.builder()
                .name("Assignment 1: Clean Architecture")
                .description("Implement a simple application using Clean Architecture principles")
                .creationDate(LocalDateTime.now().minusDays(DAYS))
                .dueDate(LocalDateTime.now().plusDays(INTERVAL))
                .gracePeriod(HOUR_IN_A_DAY)
                .supportedFileTypes(List.of(PDF, ZIP, JAVA))
                .build();
        csc207Assignments.add(assignment1);

        final Assignment assignment2 = Assignment.builder()
                .name("Assignment 2: Design Patterns")
                .description("Apply design patterns to refactor existing code")
                .creationDate(LocalDateTime.now().minusDays(DAYS_IN_2_WEEKS))
                .dueDate(LocalDateTime.now().minusDays(TWO_DAYS))
                .gracePeriod(HALF_OF_A_DAY)
                .supportedFileTypes(List.of(PDF, ZIP))
                .build();
        csc207Assignments.add(assignment2);

        final Assignment assignment3 = Assignment.builder()
                .name("Final Project")
                .description("Build a complete application demonstrating all concepts learned")
                .creationDate(LocalDateTime.now().minusDays(1))
                .dueDate(LocalDateTime.now().plusDays(DAYS_IN_2_WEEKS))
                .gracePeriod(TWO_DAYS * HOUR_IN_A_DAY)
                .supportedFileTypes(List.of(PDF, ZIP, JAVA, PY))
                .build();
        csc207Assignments.add(assignment3);

        assignmentsByCourse.put("CSC207", csc207Assignments);
    }

    public Submission getSubmission(String assignmentName, String submitter) {
        final Submission.SubmissionBuilder builder = Submission.getBuilder();
        builder.submitter("Indy");
        builder.submissionTime(LocalDateTime.now());
        builder.grade(TEST_GRADE1);
        builder.status(Submission.Status.GRADED);
        builder.feedback("Nice work!\nKeep going!");
        return builder.build();
    }

    public List<Submission> getSubmissionList(String assignmentName) {
        final List<Submission> list = new ArrayList<>();
        for (int i = 0; i < TEST_LIST_LENGTH; i++) {
            final Submission.SubmissionBuilder builder = Submission.getBuilder();
            builder.submitter("Indy");
            builder.submissionTime(LocalDateTime.now().plusSeconds(i));
            builder.status(Submission.Status.ON_TIME);
            if (Math.random() < RANDOM_THRESHOLD) {
                builder.status(Submission.Status.GRADED);
                builder.grade(Math.round(Math.random() * GRADE_MULTIPLIER) / GRADE_DIVISOR);
            }
            list.add(builder.build());
        }

        return list;
    }

    @Override
    public List<Assignment> getAssignments(String courseCode) {
        return assignmentsByCourse.getOrDefault(courseCode, new ArrayList<>());
    }

    @Override
    public User getUser() {
        return new User("instructor", "password", "Test", "Instructor", User.INSTRUCTOR);
    }

    @Override
    public String getCourseCode() {
        return "CSC207";
    }

    @Override
    public void saveAssignment(String courseCode, Assignment assignment) {
        assignmentsByCourse.computeIfAbsent(courseCode, str -> new ArrayList<>()).add(assignment);
    }

    @Override
    public void updateAssignment(String courseCode, String assignmentName, Assignment assignment) {
        final List<Assignment> assignments = assignmentsByCourse.get(courseCode);
        if (assignments != null) {
            for (int i = 0; i < assignments.size(); i++) {
                if (assignments.get(i).getName().equals(assignmentName)) {
                    assignments.set(i, assignment);
                    break;
                }
            }
        }
    }

    @Override
    public void saveFile(File saveFile, String submitter) {

    }

    @Override
    public List<Submission> getSubmissionList() {
        return List.of();
    }

    @Override
    public Submission getSubmissionForSubmissionView(String submitter) {
        return null;
    }

    @Override
    public void grade(String submitter, double grade, String feedback) {

    }
}
