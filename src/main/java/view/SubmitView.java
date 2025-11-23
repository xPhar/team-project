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

    private final String viewName = "Submit";
    private final SubmitViewModel submitViewModel;

    private final JLabel messageField = new JLabel("Click the button to submit");

    private SubmitController submitController = null;
    private ViewManagerModel viewManagerModel = null;

    public SubmitView(SubmitViewModel submitViewModel) {

        this.submitViewModel = submitViewModel;
        this.submitViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Top panel with message
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(messageField);

        // Center panel with upload button
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
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
        centerPanel.add(uploadButton);

        // Bottom panel with Back button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("← Back to Assignments");
        backButton.addActionListener(e -> {
            if (viewManagerModel != null) {
                viewManagerModel.setState("Assignments");
                viewManagerModel.firePropertyChange();
            }
        });
        bottomPanel.add(backButton);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

    }

    /**
     * Staff below is copied from CA engine, a little modification is added to
     * propertyChange so that
     * User can see whether submit successfully or error msg
     */
    public void setSubmitController(SubmitController submitController) {
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
    }

    public String getViewName() {
        return viewName;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SubmitView Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SubmitView submitView = new SubmitView(new SubmitViewModel());
        frame.setContentPane(submitView);
        frame.pack();
        frame.setVisible(true);
    }
}
