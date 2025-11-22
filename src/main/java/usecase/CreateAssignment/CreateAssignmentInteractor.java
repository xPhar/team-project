package usecase.CreateAssignment;

import entity.Assignment;

import java.time.LocalDateTime;
import java.util.List;

public class CreateAssignmentInteractor implements CreateAssignmentInputBoundary {

    private final CreateAssignmentUserDataAccessInterface assignmentDataAccess;
    private final CreateAssignmentOutputBoundary presenter;

    public CreateAssignmentInteractor(CreateAssignmentUserDataAccessInterface assignmentDataAccess,
                                      CreateAssignmentOutputBoundary presenter) {
        this.assignmentDataAccess = assignmentDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(CreateAssignmentInputData inputData) {

        String courseName = inputData.getCourseName();
        String assignmentName = inputData.getAssignmentName();

        if (assignmentName == null || assignmentName.trim().isEmpty()) {
            presenter.prepareFailView("Assignment name cannot be empty.");
            return;
        }

        if (assignmentDataAccess.existsByName(courseName, assignmentName)) {
            presenter.prepareFailView("An assignment with this name already exists for this course.");
            return;
        }

        LocalDateTime creationDate = LocalDateTime.now();

        Assignment assignment = new Assignment();
        assignment.setName(assignmentName);
        assignment.setDescription(inputData.getDescription());
        assignment.setCreationDate(creationDate);
        assignment.setDueDate(inputData.getDueDate());
        assignment.setGracePeriod(inputData.getGracePeriod());
        assignment.setLatePenalty(inputData.getLatePenalty());

        List<String> supportedFileTypes = inputData.getSupportedFileTypes();
        assignment.setSupportedFileTypes(supportedFileTypes);

        assignmentDataAccess.save(courseName, assignment);

        CreateAssignmentOutputData outputData =
                new CreateAssignmentOutputData(
                        courseName,
                        assignmentName,
                        creationDate,
                        inputData.getDueDate(),
                        "Assignment created successfully."
                );

        presenter.prepareSuccessView(outputData);
    }
}

