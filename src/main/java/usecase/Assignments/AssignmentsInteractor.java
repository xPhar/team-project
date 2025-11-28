package usecase.Assignments;

import entity.Assignment;
import entity.User.USER_TYPE;
import interface_adapter.Assignments.AssignmentDTO;

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
            assignments.sort(java.util.Comparator.comparing(Assignment::getDueDate,
                    java.util.Comparator.nullsLast(java.util.Comparator.naturalOrder())));

            boolean isInstructor = dataAccess.getCurrentUser().getUserType() == USER_TYPE.INSTRUCTOR;

            // Map Assignment entities to AssignmentDTOs
            List<AssignmentDTO> assignmentDTOs = assignments.stream()
                    .map(assignment -> new AssignmentDTO(
                            assignment.getName(),
                            assignment.getDescription(),
                            assignment.getDueDate(),
                            assignment.getGracePeriod(),
                            assignment.getSupportedFileTypes()))
                    .collect(Collectors.toList());

            AssignmentsOutputData outputData = new AssignmentsOutputData(
                    assignmentDTOs,
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
}
