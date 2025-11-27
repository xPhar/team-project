package usecase.SubmissionList;

import entity.Submission;

public interface SubmissionListOutputBoundary {
    void prepareSubmissionView(SubmissionListOutputData data);
    void goToAssignmentView();
}
