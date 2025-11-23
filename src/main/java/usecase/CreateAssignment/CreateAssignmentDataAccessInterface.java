package usecase.CreateAssignment;

import entity.Assignment;

public interface CreateAssignmentDataAccessInterface {
    void saveAssignment(String courseCode, Assignment assignment);
}
