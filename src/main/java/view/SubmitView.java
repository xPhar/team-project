package view;

import interface_adapter.Submit.SubmitController;
import interface_adapter.Submit.SubmitState;
import interface_adapter.Submit.SubmitViewModel;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.time.LocalDateTime;

public class SubmitView extends JPanel implements PropertyChangeListener {

    private static final String viewName = "submit";

    private final JLabel messageField = new JLabel("Click the button to submit");

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
        messageField.setAlignmentX(CENTER_ALIGNMENT);
        uploadButton.setAlignmentX(CENTER_ALIGNMENT);
        this.add(messageField);
        this.add(uploadButton);

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
