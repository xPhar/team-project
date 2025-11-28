package data_access;

import entity.Assignment;
import entity.Submission;
import entity.User;
import interface_adapter.submission_list.SubmissionTableModel;
import usecase.Assignments.AssignmentsDataAccessInterface;
import usecase.CreateAssignment.CreateAssignmentDataAccessInterface;
import usecase.EditAssignment.EditAssignmentDataAccessInterface;
import usecase.Grade.GradeDataAccessInterface;
import usecase.SubmissionList.SubmissionListDataAccessInterface;
import usecase.Submission.SubmissionDataAccessInterface;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDAO implements
        SubmissionListDataAccessInterface,
        GradeDataAccessInterface,
        SubmissionDataAccessInterface,
        AssignmentsDataAccessInterface,
        CreateAssignmentDataAccessInterface,
        EditAssignmentDataAccessInterface {
    private final Map<String, List<Assignment>> assignmentsByCourse = new HashMap<>();

    public TestDAO() {
        // Initialize with some sample assignments for testing
        List<Assignment> csc207Assignments = new ArrayList<>();

        Assignment assignment1 = Assignment.builder()
                .name("Assignment 1: Clean Architecture")
                .description("Implement a simple application using Clean Architecture principles")
                .creationDate(LocalDateTime.now().minusDays(7))
                .dueDate(LocalDateTime.now().plusDays(3))
                .gracePeriod(24.0)
                .supportedFileTypes(List.of("pdf", "zip", "java"))
                .build();
        csc207Assignments.add(assignment1);

        Assignment assignment2 = Assignment.builder()
                .name("Assignment 2: Design Patterns")
                .description("Apply design patterns to refactor existing code")
                .creationDate(LocalDateTime.now().minusDays(14))
                .dueDate(LocalDateTime.now().minusDays(2))
                .gracePeriod(12.0)
                .supportedFileTypes(List.of("pdf", "zip"))
                .build();
        csc207Assignments.add(assignment2);

        Assignment assignment3 = Assignment.builder()
                .name("Final Project")
                .description("Build a complete application demonstrating all concepts learned")
                .creationDate(LocalDateTime.now().minusDays(1))
                .dueDate(LocalDateTime.now().plusDays(14))
                .gracePeriod(48.0)
                .supportedFileTypes(List.of("pdf", "zip", "java", "py"))
                .build();
        csc207Assignments.add(assignment3);

        assignmentsByCourse.put("CSC207", csc207Assignments);
    }


    public Submission getSubmission(String assignmentName, String submitter) {
        Submission.SubmissionBuilder builder = Submission.getBuilder();
        builder.submitter("Indy");
        builder.submissionTime(LocalDateTime.now());
        builder.grade(20f);
        builder.status(Submission.Status.GRADED);
        builder.feedback("Nice work!\nKeep going!");
        return builder.build();
    }


    public List<Submission> getSubmissionList(String assignmentName) {
        List<Submission> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Submission.SubmissionBuilder builder = Submission.getBuilder();
            builder.submitter("Indy");
            builder.submissionTime(LocalDateTime.now().plusSeconds(i));
            builder.status(Submission.Status.ON_TIME);
            if (Math.random() < 0.8) {
                builder.status(Submission.Status.GRADED);
                builder.grade(Math.round(Math.random() * 1000) / 10.0);
            }
            list.add(builder.build());
        }

        return list;
    }

    @Override
    public void grade(String submitter, double grade, String feedback) {

    }

    @Override
    public void saveFile(File saveFile, String submitter) {

    }

    @Override
    public List<Submission> getSubmissionList() {
        return List.of();
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
        assignmentsByCourse.computeIfAbsent(courseCode, k -> new ArrayList<>()).add(assignment);
    }

    @Override
    public void updateAssignment(String courseCode, String assignmentName, Assignment assignment) {
        List<Assignment> assignments = assignmentsByCourse.get(courseCode);
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
    public Submission getSubmissionForSubmissionView(String submitter) {
        return null;
    }
}
