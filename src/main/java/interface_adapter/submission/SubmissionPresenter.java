package interface_adapter.submission;

import entity.Submission;
import usecase.Submission.SubmissionOutputBoundary;

import java.time.format.DateTimeFormatter;

/**
 * Presenter for the submission view.
 */
public class SubmissionPresenter implements SubmissionOutputBoundary {
    private final SubmissionViewModel viewModel;

    public SubmissionPresenter(SubmissionViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSubmissionView(Submission outputData) {
        SubmissionState state = new SubmissionState();
        state.setSubmitter(outputData.getSubmitter());
        state.setSubmissionDate(
                outputData.getSubmissionTime().format(
                        DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")
                )
        );
        state.setStatus(outputData.getStatus().toString());
        state.setGrade(String.format("%.1f", outputData.getGrade()));
        state.setFeedback(outputData.getFeedback());

        viewModel.setState(state);
        viewModel.firePropertyChange();
    }
}
