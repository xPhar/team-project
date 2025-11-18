package view;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SubmissionView extends JPanel implements PropertyChangeListener {
    private final JLabel submitterLabel = new JLabel("Submitter: Firstname Lastname");
    private final JLabel submittedDateLabel = new JLabel("Submitted at: 11/18/2025 10:20:23");
    private final JLabel submissionStatusLabel = new JLabel("Status: On time");
    private final JTextField gradeTextField = new JTextField();

    public SubmissionView() {
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
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
