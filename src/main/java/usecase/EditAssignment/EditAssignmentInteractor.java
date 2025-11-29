package usecase.EditAssignment;

import entity.Assignment;

import java.time.LocalDateTime;

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
        String originalName = inputData.getOriginalName();

        Assignment assignment = Assignment.builder()
                .name(inputData.getNewName())
                .description(inputData.getDescription())
                .dueDate(inputData.getDueDate())
                .supportedFileTypes(inputData.getSupportedFileTypes())
                .build();

        dataAccessObject.updateAssignment(inputData.getCourseCode(), originalName, assignment);

        outputBoundary.prepareSuccessView();
    }
}
