package view;

import interface_adapter.Resubmit.ResubmitController;
import interface_adapter.Resubmit.ResubmitState;
import interface_adapter.Resubmit.ResubmitViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;

public class ResubmitView extends JPanel implements PropertyChangeListener {

    private static final String viewName = "resubmit";

    private final JLabel messageLabel = new JLabel("Are you sure you want to resubmit?" +
            " Only the latest one will be marked");

    private ResubmitController resubmitController = null;

    public ResubmitView(ResubmitViewModel reSubmitViewModel) {

        reSubmitViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Top panel with a message
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(messageLabel);

        // Center panel with resubmit button
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);

        JButton resubmitButton = new JButton("I'm sure to resubmit");
        resubmitButton.addActionListener(e -> resubmitController.execute(LocalDateTime.now()));

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> resubmitController.goBack());

        // Styling components
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        messageLabel.setFont(labelFont);
        messageLabel.setForeground(new Color(33, 37, 41));

        resubmitButton.setFont(buttonFont);
        resubmitButton.setBackground(new Color(66, 139, 202));
        resubmitButton.setForeground(Color.WHITE);
        resubmitButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        resubmitButton.setFocusPainted(false);

        backButton.setFont(buttonFont);
        backButton.setBackground(new Color(66, 139, 202));
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        backButton.setFocusPainted(false);

        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)));

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        messageLabel.setAlignmentX(CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(resubmitButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(backButton);

        this.add(messageLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(buttonPanel);

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
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}