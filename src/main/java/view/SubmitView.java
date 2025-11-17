package view;

import interface_adapter.Submit.SubmitController;
import interface_adapter.Submit.SubmitState;
import interface_adapter.Submit.SubmitViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.time.LocalTime;

public class SubmitView extends JPanel implements PropertyChangeListener {

    private final String viewName = "Submit";
    private final SubmitViewModel submitViewModel;

    private final JLabel messageField = new JLabel("Are you sure you want to submit?");

    private SubmitController submitController = null;

    public SubmitView(SubmitViewModel submitViewModel) {

        this.submitViewModel = submitViewModel;
        this.submitViewModel.addPropertyChangeListener(this);

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
                submitController.resubmitExecute(LocalTime.now(), selectedFile);

            } else {
                JOptionPane.showMessageDialog(this, "No file selected");
            }
        });

        this.add(uploadButton);
        this.add(messageField);

    }
    /**
     * Staff below is copied from CA engine, a little modification is added to propertyChange so that
     * User can see whether submit successfully or error msg
     */
    public void setSubmitController(SubmitController submitController) {
        this.submitController = submitController;
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
}
