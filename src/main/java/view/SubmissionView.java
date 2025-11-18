package view;

import interface_adapter.submission.SubmissionState;
import interface_adapter.submission.SubmissionViewModel;
import interface_adapter.submission_list.SubmissionListViewModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Submission detail view.
 */
public class SubmissionView extends JPanel implements PropertyChangeListener {
    private final String viewName = "Submission";

    private final JLabel submitterLabel = new JLabel("Submitter:");
    private final JLabel submittedDateLabel = new JLabel("Submitted at:");
    private final JLabel submissionStatusLabel = new JLabel("Status:");
    private final JTextField gradeTextField = new JTextField();
    private final JTextArea feedbackTextArea = new JTextArea();

    private final SubmissionViewModel viewModel;

    public SubmissionView(SubmissionViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        final JButton downloadButton = new JButton("Download File");
        final JButton gradeButton = new JButton("Grade");

        final JPanel gradePanel = new JPanel();
        final JPanel feedbackPanel = new JPanel();
        gradePanel.add(new JLabel("Grade"));
        gradePanel.add(gradeTextField);
        feedbackPanel.add(gradePanel);
        feedbackPanel.add(new JLabel("Feedback:"));
        feedbackPanel.add(feedbackTextArea);
        feedbackPanel.add(gradeButton);
        feedbackPanel.setLayout(new BoxLayout(feedbackPanel, BoxLayout.Y_AXIS));

        gradeTextField.setColumns(8);
        feedbackTextArea.setRows(5);

        final JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(submitterLabel);
        infoPanel.add(submittedDateLabel);
        infoPanel.add(submissionStatusLabel);
        infoPanel.add(downloadButton);

        final JButton backButton = new JButton("Back");

        this.setLayout(new BoxLayout(this,  BoxLayout.Y_AXIS));
        this.add(backButton);
        this.add(infoPanel);
        this.add(feedbackPanel);

        populateTestData();
    }

    private void populateTestData() {
        viewModel.getState().setSubmitter("Indy");
        viewModel.getState().setStatus("Grading");
        viewModel.getState().setSubmissionDate("11/18/2025 12:30:30");
        viewModel.getState().setGrade("20");
        viewModel.getState().setFeedback("This is a sample feedback.\n You did great!");
        viewModel.firePropertyChange();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            SubmissionState submissionState = (SubmissionState) evt.getNewValue();
            submitterLabel.setText("Submitter: " + submissionState.getSubmitter());
            submittedDateLabel.setText("Submitted at: " + submissionState.getSubmissionDate());
            submissionStatusLabel.setText("Status: " + submissionState.getStatus());
            gradeTextField.setText(submissionState.getGrade());
            feedbackTextArea.setText(submissionState.getFeedback());
        }

    }

    public String getViewName() {
        return viewName;
    }
}
