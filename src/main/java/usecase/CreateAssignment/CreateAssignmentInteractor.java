package usecase.CreateAssignment;

import entity.Assignment;
import usecase.Assignments.AssignmentsInputBoundary;
import usecase.Assignments.AssignmentsInputData;

import java.time.LocalDateTime;

public class CreateAssignmentInteractor implements CreateAssignmentInputBoundary {
    private final CreateAssignmentDataAccessInterface dataAccess;
    private final CreateAssignmentOutputBoundary presenter;
    private final AssignmentsInputBoundary assignmentsInputBoundary;

    public CreateAssignmentInteractor(CreateAssignmentDataAccessInterface dataAccess,
                                      CreateAssignmentOutputBoundary presenter,
                                      AssignmentsInputBoundary assignmentsInputBoundary) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
        this.assignmentsInputBoundary = assignmentsInputBoundary;
    }

    @Override
    public void execute(CreateAssignmentInputData inputData) {
        try {
            if (inputData.getName().isEmpty()) {
                presenter.prepareFailureView("Assignment name cannot be empty.");
                return;
            }
            // Additional validation logic can go here

            // Build Assignment entity from DTO fields
            Assignment assignment = Assignment.builder()
                    .name(inputData.getName())
                    .description(inputData.getDescription())
                    .dueDate(inputData.getDueDate())
                    .creationDate(LocalDateTime.now())
                    .gracePeriod(inputData.getGracePeriod())
                    .supportedFileTypes(inputData.getSupportedFileTypes())
                    .build();

            dataAccess.saveAssignment(inputData.getCourseCode(), assignment);

            CreateAssignmentOutputData outputData = new CreateAssignmentOutputData();
            presenter.prepareSuccessView(outputData);
            assignmentsInputBoundary.execute(new AssignmentsInputData());
        } catch (Exception e) {
            presenter.prepareFailureView("Failed to create assignment: " + e.getMessage());
        }
    }

    @Override
    public void switchToAssignmentView() {
        presenter.switchToAssignmentView();
    }
}