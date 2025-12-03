package usecase.edit_assignment;

import entity.Assignment;



public class EditAssignmentInteractor implements EditAssignmentInputBoundary {
    private final EditAssignmentDataAccessInterface dataAccessObject;
    private final EditAssignmentOutputBoundary outputBoundary;

    public EditAssignmentInteractor(EditAssignmentDataAccessInterface dataAccessObject,
                                    EditAssignmentOutputBoundary outputBoundary){
        this.dataAccessObject = dataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(EditAssignmentInputData inputData) {
        String originalName = inputData.getOriginalName();

        Assignment assignment = Assignment.builder()
                .name(inputData.getNewName())
                .description(inputData.getDescription())
                .dueDate(inputData.getDueDate())
                .supportedFileTypes(inputData.getSupportedFileTypes())
                .build();

        try {
            dataAccessObject.updateAssignment(inputData.getCourseCode(), originalName, assignment);

            outputBoundary.prepareSuccessView();

        } catch (RuntimeException e) {
            outputBoundary.prepareFailureView(e.getMessage());
        }
    }
}