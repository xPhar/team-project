package usecase.SubmissionList;

import entity.Submission;

public interface SubmissionListOutputBoundary {
    void prepareListView(SubmissionListOutputData submissionListOutputData);
    void prepareSubmissionView(Submission outputData);
}
