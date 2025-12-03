package usecase.grade;

public class GradeInputData {
    private final String grade;
    private final String submitter;
    private final String feedback;

    public GradeInputData(String grade, String submitter, String feedback) {
        this.grade = grade;
        this.submitter = submitter;
        this.feedback = feedback;
    }

    public String getGrade() {
        return grade;
    }

    public String getSubmitter() {
        return submitter;
    }

    public String getFeedback() {
        return feedback;
    }
}
