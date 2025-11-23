package usecase.EditAssignment;

import entity.Assignment;

public interface EditAssignmentDataAccessInterface {
    void saveAssignment(String courseCode, Assignment assignment);

    Assignment getAssignment(String courseCode, String assignmentName);

    void deleteAssignment(String courseCode, String assignmentName);
}
