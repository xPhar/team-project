package usecase.SubmissionList;

public class SubmissionListInputData {
    private final boolean back;
    private final String submitter;
    private final String assignmentName;

    public SubmissionListInputData(
            boolean back,
            String submitter,
            String assignmentName
    ) {
        this.back = back;
        this.submitter = submitter;
        this.assignmentName = assignmentName;
    }

    public boolean isBack() {
        return back;
    }

    public String getSubmitter() {
        return submitter;
    }

    public String getAssignmentName() {
        return assignmentName;
    }
}
