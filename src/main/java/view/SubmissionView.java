package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import interfaceadapter.submission.SubmissionController;
import interfaceadapter.submission.SubmissionState;
import interfaceadapter.submission.SubmissionViewModel;

/**
 * Submission detail view.
 */
public class SubmissionView extends JPanel implements PropertyChangeListener {
    private static final int BORDER_SIZE = 10;
    private static final int FEEDBACKPANEL_MAX_W = 500;
    private static final int GRADE_TEXT_FIELD_COLUMNS = 8;
    private static final int FEEDBACK_TEXT_FIELD_ROWS = 3;
    private static final int FEEDBACK_TEXT_FIELD_COLUMNS = 5;
    private static final int FEEDBACK_TEXT_FIELD_BORDER = 5;
    private static final int TITLE_FONT = 20;
    private static final int FONT_SIZE = 20;
    private static final int BUTTON_PANEL_MAX_H = 20;

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
        gradePanel.setBorder(
                BorderFactory.createEmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE)
        );
        feedbackPanel.setLayout(new BorderLayout());
        feedbackPanel.add(new JLabel("Feedback:"), BorderLayout.NORTH);
        feedbackPanel.add(new JScrollPane(feedbackTextArea), BorderLayout.CENTER);
        feedbackPanel.setMaximumSize(
                new Dimension(FEEDBACKPANEL_MAX_W, feedbackPanel.getMaximumSize().height)
        );
        feedbackPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        gradeTextField.setColumns(GRADE_TEXT_FIELD_COLUMNS);
        feedbackTextArea.setRows(FEEDBACK_TEXT_FIELD_ROWS);
        feedbackTextArea.setColumns(FEEDBACK_TEXT_FIELD_COLUMNS);
        feedbackTextArea.setBorder(
                BorderFactory.createEmptyBorder(
                        FEEDBACK_TEXT_FIELD_BORDER,
                        FEEDBACK_TEXT_FIELD_BORDER,
                        FEEDBACK_TEXT_FIELD_BORDER,
                        FEEDBACK_TEXT_FIELD_BORDER)
        );

        final JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(submitterLabel);
        infoPanel.add(submissionNameLabel);
        infoPanel.add(submittedDateLabel);
        infoPanel.add(submissionStatusLabel);
        infoPanel.add(downloadButton);
        infoPanel.setBorder(
                BorderFactory.createEmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE)
        );

        title = new JLabel("");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Helvetica", Font.BOLD, TITLE_FONT));

        final JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBorder(
                BorderFactory.createEmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE)
        );
        titlePanel.add(title);

        final JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE)
        );
        buttonPanel.setMaximumSize(
                new Dimension(buttonPanel.getMaximumSize().width, BUTTON_PANEL_MAX_H));
        buttonPanel.add(backButton, BorderLayout.WEST);
        buttonPanel.add(gradeButton, BorderLayout.EAST);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(titlePanel);
        this.add(infoPanel);
        this.add(gradePanel);
        this.add(feedbackPanel);
        this.add(buttonPanel);
        this.setFont(new Font("Helvetica", Font.PLAIN, FONT_SIZE));

        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gradePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

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
                        final String grade = gradeTextField.getText();
                        final String submitter = submitterLabel.getText();
                        final String feedback = feedbackTextArea.getText();

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
            final SubmissionState submissionState = (SubmissionState) evt.getNewValue();
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
            final SubmissionState submissionState = (SubmissionState) evt.getNewValue();
            showFailureDialog(submissionState.getGradeFailureMessage(), "Failure");
        }
        else if (evt.getPropertyName().equals("downloadSuccess")) {
            final SubmissionState submissionState = (SubmissionState) evt.getNewValue();
            showSuccessDialog(submissionState.getDownloadSuccessMessage(), "Success");
        }
        else if (evt.getPropertyName().equals("downloadFailure")) {
            final SubmissionState submissionState = (SubmissionState) evt.getNewValue();
            showSuccessDialog(submissionState.getDownloadFailureMessage(), "Failure");
        }
    }

    private void showSuccessDialog(String msg, String MsgTitle) {
        JOptionPane.showMessageDialog(this,
                msg,
                MsgTitle,
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showFailureDialog(String msg, String MsgTitle) {
        JOptionPane.showMessageDialog(this,
                msg,
                MsgTitle,
                JOptionPane.ERROR_MESSAGE);
    }

    private void chooseFilePathToSave() {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Download file");
        fileChooser.setSelectedFile(new File(submissionNameLabel.getText().substring(11)));
        final int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            final File selectedFile = fileChooser.getSelectedFile();
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
