package usecase.Assignments;

import entity.Assignment;
import entity.Course;
import entity.Instructor;
import entity.User;

import java.util.List;

public class AssignmentsInteractor implements AssignmentsInputBoundary {
    private final AssignmentsDataAccessInterface dataAccess;
    private final AssignmentsOutputBoundary presenter;

    public AssignmentsInteractor(AssignmentsDataAccessInterface dataAccess,
            AssignmentsOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(AssignmentsInputData inputData) {
        try {
            Course course = dataAccess.getCourse();
            User user = dataAccess.getUser();

            if (course == null) {
                presenter.prepareFailureView("No course selected");
                return;
            }

            if (user == null) {
                presenter.prepareFailureView("No user logged in");
                return;
            }

            List<Assignment> assignments = dataAccess.getAssignments(course.getCourseCode());
            assignments.sort(java.util.Comparator.comparing(Assignment::getDueDate,
                    java.util.Comparator.nullsLast(java.util.Comparator.naturalOrder())));
            boolean isInstructor = user instanceof Instructor;

            AssignmentsOutputData outputData = new AssignmentsOutputData(
                    assignments,
                    course.getCourseName(),
                    isInstructor);

            presenter.prepareSuccessView(outputData);

        } catch (Exception e) {
            presenter.prepareFailureView("Error loading assignments: " + e.getMessage());
        }
    }
}
