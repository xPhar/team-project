package view;

import interface_adapter.Resubmit.ResubmitController;
import interface_adapter.Resubmit.ResubmitState;
import interface_adapter.Resubmit.ResubmitViewModel;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;

public class ResubmitView extends JPanel implements PropertyChangeListener {

    private final String viewName = "Resubmit";
    private final ResubmitViewModel resubmitViewModel;
    private final JLabel messageLabel = new JLabel("Are you sure you want to resubmit?" +
            " Only the latest one will be marked.");
    private ResubmitController resubmitController = null;
    private ViewManagerModel viewManagerModel = null;

    public ResubmitView(ResubmitViewModel reSubmitViewModel) {

        this.resubmitViewModel = reSubmitViewModel;
        this.resubmitViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Top panel with message
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(messageLabel);

        // Center panel with resubmit button
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton resubmitButton = new JButton("I'm sure to resubmit");
        resubmitButton.addActionListener(e -> {
            if (resubmitController != null) {
                resubmitController.execute(LocalDateTime.now());
            }
        });
        centerPanel.add(resubmitButton);

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

    public void setResubmitController(ResubmitController resubmitController) {
        this.resubmitController = resubmitController;
    }

    public void setViewManagerModel(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ResubmitState state = (ResubmitState) evt.getNewValue();
        setFields(state);
        messageLabel.setForeground(state.getMsgColor());

    }

    private void setFields(ResubmitState state) {
        messageLabel.setText(state.getMessage());
    }

    public String getViewName() {
        return viewName;
    }

    // This is a seperated page for demo only
    public static void main(String[] args) {
        JFrame frame = new JFrame("Resubmit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ResubmitView view = new ResubmitView(new ResubmitViewModel());
        frame.setContentPane(view);
        frame.pack();
        frame.setVisible(true);
    }
}