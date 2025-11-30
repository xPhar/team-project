package view;

import interface_adapter.submission.SubmissionController;
import interface_adapter.submission.SubmissionState;
import interface_adapter.submission.SubmissionViewModel;

import javax.swing.*;
import java.awt.*;
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
    private final JLabel submissionNameLabel = new JLabel("File name:");
    private final JLabel submittedDateLabel = new JLabel("Submitted at:");
    private final JLabel submissionStatusLabel = new JLabel("Status:");
    private final JTextField gradeTextField = new JTextField();
    private final JLabel maxGradeLabel = new JLabel("/20");
    private final JTextArea feedbackTextArea = new JTextArea();
    private final JLabel title;

    private final SubmissionViewModel viewModel;

    public SubmissionView(SubmissionViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        final JButton downloadButton = new JButton("Download File");
        final JButton gradeButton = new JButton("Grade");
        final JButton backButton = new JButton("Back");

        final JPanel gradePanel = new JPanel();
        final JPanel feedbackPanel = new JPanel();
        gradePanel.add(new JLabel("Grade"));
        gradePanel.add(gradeTextField);
        gradePanel.add(maxGradeLabel);
        gradePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        feedbackPanel.setLayout(new BorderLayout());
        feedbackPanel.add(new JLabel("Feedback:"), BorderLayout.NORTH);
        feedbackPanel.add(new JScrollPane(feedbackTextArea), BorderLayout.CENTER);
//        feedbackPanel.setMaximumSize(new Dimension(500,feedbackPanel.getMaximumSize().height));

        gradeTextField.setColumns(8);
        feedbackTextArea.setRows(3);
        feedbackTextArea.setColumns(5);
        feedbackTextArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        final JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(submitterLabel);
        infoPanel.add(submissionNameLabel);
        infoPanel.add(submittedDateLabel);
        infoPanel.add(submissionStatusLabel);
        infoPanel.add(downloadButton);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        title = new JLabel("");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Helvetica", Font.BOLD, 20));

        final JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titlePanel.add(title);

        final JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setMaximumSize(new Dimension(buttonPanel.getMaximumSize().width, 80));
        buttonPanel.add(backButton, BorderLayout.WEST);
        buttonPanel.add(gradeButton, BorderLayout.EAST);

        this.setLayout(new BoxLayout(this,  BoxLayout.Y_AXIS));
        this.add(titlePanel);
        this.add(infoPanel);
        this.add(gradePanel);
        this.add(feedbackPanel);
        this.add(buttonPanel);
        this.setFont(new Font("Helvetica", Font.PLAIN, 16));

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
            title.setText(submissionState.getAssignmentName());
            maxGradeLabel.setText("/" + submissionState.getMaxGrade());
            submissionNameLabel.setText("File name: " + submissionState.getSubmissionName());
        }
        else if (evt.getPropertyName().equals("gradeSuccess")) {
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
        fileChooser.setSelectedFile(new File(submissionNameLabel.getText().substring(11)));
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            submissionController.executeDownload(selectedFile, submitterLabel.getText());
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
