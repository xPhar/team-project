package view;

import interface_adapter.Resubmit.ResubmitController;
import interface_adapter.Resubmit.ResubmitState;
import interface_adapter.Resubmit.ResubmitViewModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;

public class ResubmitView extends JPanel implements PropertyChangeListener {

    private final String viewName = "Resubmit";
    private final ResubmitViewModel resubmitViewModel;
    private final JLabel messageLabel = new JLabel("Are you sure you want to resubmit?" +
            " Only the latest one will be marked.");
    private ResubmitController resubmitController = null;

    public ResubmitView(ResubmitViewModel reSubmitViewModel) {

        this.resubmitViewModel = reSubmitViewModel;
        this.resubmitViewModel.addPropertyChangeListener(this);

        JButton resubmitButton = new JButton("I'm sure to resubmit");
        resubmitButton.addActionListener(e -> resubmitController.execute(LocalDateTime.now()));

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(messageLabel);
        this.add(resubmitButton);

    }

    public void setResubmitController(ResubmitController resubmitController) {
        this.resubmitController = resubmitController;
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