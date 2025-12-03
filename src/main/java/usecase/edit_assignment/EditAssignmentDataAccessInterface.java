package usecase.edit_assignment;

import entity.Assignment;

public interface EditAssignmentDataAccessInterface {
    void updateAssignment(String courseCode, String originalAssignmentName, Assignment assignment);
}
