package usecase.SubmissionList;

import entity.Submission;

import java.util.List;

public class SubmissionListOutputData {
    private final String assignmentName;
    private final List<Submission> submissions;

    public SubmissionListOutputData(String assignmentName, List<Submission> submissions) {
        this.assignmentName = assignmentName;
        this.submissions = submissions;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public String getAssignmentName() {
        return assignmentName;
    }
}
