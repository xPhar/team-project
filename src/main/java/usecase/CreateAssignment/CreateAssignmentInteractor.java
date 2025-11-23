package usecase.CreateAssignment;

<<<<<<< HEAD
public class CreateAssignmentInteractor implements CreateAssignmentInputBoundary {
    private final CreateAssignmentDataAccessInterface dataAccess;
    private final CreateAssignmentOutputBoundary presenter;

    public CreateAssignmentInteractor(CreateAssignmentDataAccessInterface dataAccess,
            CreateAssignmentOutputBoundary presenter) {
        this.dataAccess = dataAccess;
=======
import entity.Assignment;

import java.time.LocalDateTime;
import java.util.List;

public class CreateAssignmentInteractor implements CreateAssignmentInputBoundary {

    private final CreateAssignmentUserDataAccessInterface assignmentDataAccess;
    private final CreateAssignmentOutputBoundary presenter;

    public CreateAssignmentInteractor(CreateAssignmentUserDataAccessInterface assignmentDataAccess,
                                      CreateAssignmentOutputBoundary presenter) {
        this.assignmentDataAccess = assignmentDataAccess;
>>>>>>> b9a7cc9bb1bdc914e096b401146dd992396e05d2
        this.presenter = presenter;
    }

    @Override
    public void execute(CreateAssignmentInputData inputData) {
<<<<<<< HEAD
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
=======

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

>>>>>>> b9a7cc9bb1bdc914e096b401146dd992396e05d2
