package usecase.CreateAssignment;

import entity.Assignment;

public class CreateAssignmentInteractor implements CreateAssignmentInputBoundary {
    private final CreateAssignmentDataAccessInterface dataAccess;
    private final CreateAssignmentOutputBoundary presenter;

    public CreateAssignmentInteractor(CreateAssignmentDataAccessInterface dataAccess,
            CreateAssignmentOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
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
                    .gracePeriod(inputData.getGracePeriod())
                    .supportedFileTypes(inputData.getSupportedFileTypes())
                    .build();

            dataAccess.saveAssignment(inputData.getCourseCode(), assignment);

            CreateAssignmentOutputData outputData = new CreateAssignmentOutputData();
            presenter.prepareSuccessView(outputData);
        } catch (Exception e) {
            presenter.prepareFailureView("Failed to create assignment: " + e.getMessage());
        }
    }

    @Override
    public void switchToAssignmentView() {
        presenter.switchToAssignmentView();
    }
}
