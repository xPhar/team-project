package usecase.SubmissionList;

public class SubmissionListInputData {
    private final boolean back;
    private final String submitter;

    public SubmissionListInputData(
            boolean back,
            String submitter) {
        this.back = back;
        this.submitter = submitter;
    }


    public boolean isBack() {
        return back;
    }

    public String getSubmitter() {
        return submitter;
    }
}
