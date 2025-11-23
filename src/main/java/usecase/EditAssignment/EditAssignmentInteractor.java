package usecase.EditAssignment;

import entity.Assignment;

public class EditAssignmentInteractor implements EditAssignmentInputBoundary {
    private final EditAssignmentDataAccessInterface dataAccessObject;
    private final EditAssignmentOutputBoundary outputBoundary;

    public EditAssignmentInteractor(EditAssignmentDataAccessInterface dataAccessObject,
            EditAssignmentOutputBoundary outputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(EditAssignmentInputData inputData) {
        if (inputData.getNewName().isEmpty()) {
            outputBoundary.prepareFailureView("Assignment name cannot be empty.");
            return;
        }


        if (!inputData.getOriginalName().equals(inputData.getNewName())) {
            Assignment existing = dataAccessObject.getAssignment(inputData.getCourseCode(), inputData.getNewName());
            if (existing != null) {
                outputBoundary.prepareFailureView("Assignment with this name already exists.");
                return;
            }

            dataAccessObject.deleteAssignment(inputData.getCourseCode(), inputData.getOriginalName());
        }

        Assignment assignment = new Assignment();
        assignment.setName(inputData.getNewName());
        assignment.setDescription(inputData.getDescription());
        assignment.setDueDate(inputData.getDueDate());
        assignment.setGracePeriod(inputData.getGracePeriod());
        assignment.setLatePenalty(inputData.getLatePenalty());
        assignment.setSupportedFileTypes(inputData.getSupportedFileTypes());



        Assignment originalAssignment = dataAccessObject.getAssignment(inputData.getCourseCode(),
                inputData.getOriginalName());
        if (originalAssignment != null) {
            assignment.setCreationDate(originalAssignment.getCreationDate());
        } else {

            assignment.setCreationDate(java.time.LocalDateTime.now());
        }

        dataAccessObject.saveAssignment(inputData.getCourseCode(), assignment);

        EditAssignmentOutputData outputData = new EditAssignmentOutputData(assignment, inputData.getCourseCode(),
                false);
        outputBoundary.prepareSuccessView(outputData);
    }
}
