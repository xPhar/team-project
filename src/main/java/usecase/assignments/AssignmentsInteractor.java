package usecase.assignments;

import entity.Assignment;
import entity.Submission;
import entity.User.UserType;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class AssignmentsInteractor implements AssignmentsInputBoundary {
    private final AssignmentsDataAccessInterface dataAccess;
    private final AssignmentsOutputBoundary presenter;

    public AssignmentsInteractor(AssignmentsDataAccessInterface dataAccess,
            AssignmentsOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(AssignmentsInputData inputData) {
        try {
            List<Assignment> assignments = dataAccess.getAssignments();

            boolean isInstructor = dataAccess.getCurrentUser().getUserType() == UserType.INSTRUCTOR;

            // Map Assignment entities to AssignmentDTOs
            List<AssignmentDataTransferObject> assignmentDataTransferObjects = assignments.stream()
                    .sorted(java.util.Comparator.comparing(Assignment::getDueDate,
                            java.util.Comparator.nullsLast(java.util.Comparator.naturalOrder())))
                    .map(assignment -> new AssignmentDataTransferObject(
                            assignment.getName(),
                            assignment.getDescription(),
                            assignment.getDueDate(),
                            assignment.getGracePeriod(),
                            assignment.getSupportedFileTypes()))
                    .collect(Collectors.toList());

            AssignmentsOutputData outputData = new AssignmentsOutputData(
                    assignmentDataTransferObjects,
                    dataAccess.getCourseCode(),
                    isInstructor);

            presenter.prepareSuccessView(outputData);
        } catch (Exception e) {
            presenter.prepareFailureView("Error loading assignments: " + e.getMessage());
        }
    }


    @Override
    public void switchToCreateAssignmentView() {
        presenter.switchToCreateAssignmentView();
    }

    @Override
    public void switchToSubmitView() {
        presenter.switchToSubmitView();
    }

    @Override
    public void switchToResubmitView() {
        presenter.switchToResubmitView();
    }

    @Override
    public void switchToSubmissionListView(String assignmentName) {
        Assignment assignment = dataAccess.getAssignment(assignmentName);
        dataAccess.setActiveAssignment(assignment);

        AssignmentsOutputData outputData = new AssignmentsOutputData(
                assignment.getName(),
                getSubmissionText(assignment)
        );

        presenter.switchToSubmissionListView(outputData);
    }

    @Override
    public void switchToLoginView() {
        String username = dataAccess.getCurrentUser().getName();

        dataAccess.resetSession();

        AssignmentsOutputData outputData = new AssignmentsOutputData(username);

        presenter.switchToLoginView(outputData);
    }

    private String[][] getSubmissionText(Assignment assignment) {
        List<Submission> submissions = dataAccess.getSubmissionList(assignment);
        String[][] submissionText = new String[submissions.size()][];
        for (int i = 0; i < submissions.size(); i++) {
            Submission submission = submissions.get(i);

            String gradeString = "pending";
            Submission.Status status = submission.getStatus();
            if (status == Submission.Status.GRADED) {
                gradeString = String.format("%.1f", submission.getGrade());
            }

            submissionText[i] = new String[]{
                    submission.getSubmitter(),
                    submission.getSubmissionTime().format(
                            DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")
                    ),
                    gradeString
            };
        }
        return submissionText;
    }
}
