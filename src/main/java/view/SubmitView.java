package view;

import interface_adapter.Submit.SubmitController;
import interface_adapter.Submit.SubmitState;
import interface_adapter.Submit.SubmitViewModel;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.time.LocalDateTime;

public class SubmitView extends JPanel implements PropertyChangeListener {

    private static final String viewName = "submit";

    private final JLabel messageField = new JLabel("Click the button to submit");
    private final JLabel assignmentNameLabel = new JLabel("Assignment: ");
    private final JLabel AssignmentDescriptionLabel = new JLabel("Assignment Description:");
    private final JLabel dueDateLabel = new JLabel("Due Date: ");


    private SubmitController submitController = null;
    private ViewManagerModel viewManagerModel = null;

    /**
     * Initialize SubmitView (Subclass of JPanel)
     * 
     * @param submitViewModel Corresponding model storing its changing property
     */
    public SubmitView(SubmitViewModel submitViewModel) {

        submitViewModel.addPropertyChangeListener(this);

        JButton uploadButton = new JButton("Choose File");
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select a file to upload");
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(this,
                        "File selected: " + selectedFile.getAbsolutePath());
                // Then go to controller
                if (submitController != null) {
                    submitController.submitExecute(LocalDateTime.now(), selectedFile);
                }

            } else {
                JOptionPane.showMessageDialog(this, "No file selected");
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(25, 30, 25, 30)));
        this.setBackground(Color.WHITE);

        assignmentNameLabel.setAlignmentX(CENTER_ALIGNMENT);
        AssignmentDescriptionLabel.setAlignmentX(CENTER_ALIGNMENT);
        dueDateLabel.setAlignmentX(CENTER_ALIGNMENT);
        messageField.setAlignmentX(CENTER_ALIGNMENT);
        uploadButton.setAlignmentX(CENTER_ALIGNMENT);

        Font titleFont = new Font("Segoe UI", Font.BOLD, 16);
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);

        assignmentNameLabel.setFont(titleFont);
        AssignmentDescriptionLabel.setFont(labelFont);
        dueDateLabel.setFont(labelFont);
        messageField.setFont(labelFont);

        uploadButton.setFont(labelFont);
        uploadButton.setBackground(new Color(66, 139, 202));
        uploadButton.setForeground(Color.WHITE);
        uploadButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        uploadButton.setFocusPainted(false);

        this.add(Box.createVerticalStrut(15));
        this.add(assignmentNameLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(AssignmentDescriptionLabel);
        this.add(Box.createVerticalStrut(15));
        this.add(dueDateLabel);
        this.add(Box.createVerticalStrut(25));
        this.add(messageField);
        this.add(Box.createVerticalStrut(25));
        this.add(uploadButton);
    }

    /**
     * Staff below is copied from CA engine, a little modification is added to
     * propertyChange so that
     * User can see whether submit successfully or error msg
     */
    public void setSubmitController(final SubmitController submitController) {
        this.submitController = submitController;
    }

    public void setViewManagerModel(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SubmitState state = (SubmitState) evt.getNewValue();
        setFields(state);
        messageField.setForeground(state.getMsgColor());
    }

    private void setFields(SubmitState state) {
        messageField.setText(state.getMessage());
        assignmentNameLabel.setText("Assignment: " + state.getAssignmentName());
        AssignmentDescriptionLabel.setText("Assignment Description: " + state.getAssignmentDescription());
        dueDateLabel.setText("Due Date: " + state.getDueDate());
    }

    public String getViewName() {
        return viewName;
    }

    /**
     * Show the UI only
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("SubmitView Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SubmitView submitView = new SubmitView(new SubmitViewModel());
        frame.setContentPane(submitView);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
