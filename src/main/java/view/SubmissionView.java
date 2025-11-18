package view;

import interface_adapter.submission.SubmissionState;
import interface_adapter.submission.SubmissionViewModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Submission detail view.
 */
public class SubmissionView extends JPanel implements PropertyChangeListener {
    private final JLabel submitterLabel = new JLabel("Submitter:");
    private final JLabel submittedDateLabel = new JLabel("Submitted at:");
    private final JLabel submissionStatusLabel = new JLabel("Status:");
    private final JTextField gradeTextField = new JTextField();

    private final SubmissionViewModel viewModel;

    public SubmissionView(SubmissionViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        final JButton downloadButton = new JButton("Download File");
        final JButton gradeButton = new JButton("Grade");

        final JPanel gradePanel = new JPanel();
        gradePanel.add(gradeTextField);
        gradePanel.add(gradeButton);

        gradeTextField.setColumns(5);

        final JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(submitterLabel);
        infoPanel.add(submittedDateLabel);
        infoPanel.add(submissionStatusLabel);
        infoPanel.add(downloadButton);

        this.setLayout(new BoxLayout(this,  BoxLayout.Y_AXIS));
        this.add(infoPanel);
        this.add(gradePanel);

        populateTestData();
    }

    private void populateTestData() {
        viewModel.getState().setSubmitter("Indy");
        viewModel.getState().setStatus("Grading");
        viewModel.getState().setSubmissionDate("11/18/2025 12:30:30");
        viewModel.getState().setGrade("20");
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
        }

    }
}
