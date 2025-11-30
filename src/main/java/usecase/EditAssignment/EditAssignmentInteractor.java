package usecase.EditAssignment;

import entity.Assignment;
import usecase.Assignments.AssignmentsInputBoundary;
import usecase.Assignments.AssignmentsInputData;



public class EditAssignmentInteractor implements EditAssignmentInputBoundary {
    private final EditAssignmentDataAccessInterface dataAccessObject;
    private final EditAssignmentOutputBoundary outputBoundary;
    private final AssignmentsInputBoundary assignmentsInputBoundary;

    public EditAssignmentInteractor(EditAssignmentDataAccessInterface dataAccessObject,
                                    EditAssignmentOutputBoundary outputBoundary,
                                    AssignmentsInputBoundary assignmentsInputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.outputBoundary = outputBoundary;
        this.assignmentsInputBoundary = assignmentsInputBoundary;
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
        assignmentsInputBoundary.execute(new AssignmentsInputData());
    }
}