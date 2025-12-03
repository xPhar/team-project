package usecase.create_assignment;

import entity.Assignment;

public interface CreateAssignmentUserDataAccessInterface {

    boolean existsByName(String courseName, String assignmentName);

    void save(String courseName, Assignment assignment);
}

