package usecase.EditAssignment;

import entity.Assignment;

public interface EditAssignmentDataAccessInterface {
    void updateAssignment(String courseCode, String assignmentName, Assignment assignment);
}
