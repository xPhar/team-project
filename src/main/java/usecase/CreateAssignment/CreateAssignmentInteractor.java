package usecase.CreateAssignment;

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
            if (inputData.getAssignment().getName().isEmpty()) {
                presenter.prepareFailureView("Assignment name cannot be empty.");
                return;
            }
            // Additional validation logic can go here

            dataAccess.saveAssignment(inputData.getCourseCode(), inputData.getAssignment());

            CreateAssignmentOutputData outputData = new CreateAssignmentOutputData(
                    inputData.getAssignment(),
                    "Assignment created successfully.",
                    true);
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
