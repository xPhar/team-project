package usecase.create_assignment;

import entity.Assignment;

public interface CreateAssignmentDataAccessInterface {
    void saveAssignment(String courseCode, Assignment assignment);
}
