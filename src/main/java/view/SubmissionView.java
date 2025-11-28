package view;

import interface_adapter.submission.SubmissionController;
import interface_adapter.submission.SubmissionState;
import interface_adapter.submission.SubmissionViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.time.LocalTime;

/**
 * Submission detail view.
 */
public class SubmissionView extends JPanel implements PropertyChangeListener {
    private final String viewName = "submission";
    private SubmissionController submissionController;

    private final JLabel submitterLabel = new JLabel("Submitter:");
    private final JLabel submittedDateLabel = new JLabel("Submitted at:");
    private final JLabel submissionStatusLabel = new JLabel("Status:");
    private final JTextField gradeTextField = new JTextField();
    private final JLabel maxGradeLabel = new JLabel("/20");
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
        gradePanel.add(maxGradeLabel);
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

        backButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        submissionController.executeBack();
                    }
                }
        );

        gradeButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String grade = gradeTextField.getText();
                        String submitter = submitterLabel.getText();
                        String feedback = feedbackTextArea.getText();

                        submissionController.executeGrade(grade, submitter, feedback);
                    }
                }
        );
        downloadButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        chooseFilePathToSave();
                    }
                }
        );
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
        else if (evt.getPropertyName().equals("gradSuccess")) {
            showSuccessDialog("Graded submission successfully", "Success");
        }
        else if (evt.getPropertyName().equals("gradeFailure")) {
            SubmissionState submissionState = (SubmissionState) evt.getNewValue();
            showFailureDialog(submissionState.getGradeFailureMessage(), "Failure");
        }
        else if (evt.getPropertyName().equals("downloadSuccess")) {
            SubmissionState submissionState = (SubmissionState) evt.getNewValue();
            showSuccessDialog(submissionState.getDownloadSuccessMessage(), "Success");
        }
        else if (evt.getPropertyName().equals("downloadFailure")) {
            SubmissionState submissionState = (SubmissionState) evt.getNewValue();
            showSuccessDialog(submissionState.getDownloadFailureMessage(), "Failure");
        }
    }

    private void showSuccessDialog(String msg, String title) {
        JOptionPane.showMessageDialog(this,
                msg,
                title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showFailureDialog(String msg, String title) {
        JOptionPane.showMessageDialog(this,
                msg,
                title,
                JOptionPane.ERROR_MESSAGE);
    }

    private void chooseFilePathToSave() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Download file");
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            submissionController.executeDownload(selectedFile);
        }
    }

    public String getViewName() {
        return viewName;
    }

    public SubmissionController getSubmissionController() {
        return submissionController;
    }

    public void setSubmissionController(SubmissionController submissionController) {
        this.submissionController = submissionController;
    }
}
