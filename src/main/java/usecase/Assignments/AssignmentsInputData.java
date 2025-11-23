package usecase.Assignments;

public class AssignmentsInputData {
    private final String courseCode;

    public AssignmentsInputData(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseCode() {
        return courseCode;
    }
}
