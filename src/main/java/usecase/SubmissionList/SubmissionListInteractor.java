package usecase.SubmissionList;

import entity.Submission;
import interface_adapter.ViewManagerModel;

import java.util.List;

public class SubmissionListInteractor implements SubmissionListInputBoundary {
    private final SubmissionListOutputBoundary submissionListOutputBoundary;
    private final SubmissionListDataAccessInterface submissionListDataAccessInterface;

    public SubmissionListInteractor(
            SubmissionListOutputBoundary submissionListOutputBoundary,
            SubmissionListDataAccessInterface submissionListDataAccessInterface
    ) {
        this.submissionListOutputBoundary = submissionListOutputBoundary;
        this.submissionListDataAccessInterface = submissionListDataAccessInterface;
    }

    @Override
    public void getSubmissionList(String assignmentName) {
        List<Submission> submissions =
                submissionListDataAccessInterface.getSubmissionList(assignmentName);

        SubmissionListOutputData outputData =
                new SubmissionListOutputData(assignmentName, submissions);

        submissionListOutputBoundary.prepareListView(outputData);
    }

    @Override
    public void backToSubmissionList() {
        submissionListOutputBoundary.showListView();
    }
}
