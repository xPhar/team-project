package usecase.CreateAssignment;

import entity.Assignment;

public interface CreateAssignmentUserDataAccessInterface {

    boolean existsByName(String courseName, String assignmentName);

    void save(String courseName, Assignment assignment);
}

