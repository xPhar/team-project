package view;

import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for when the user is logged in to the program.
 */
public class LoggedInView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "logged in";
    private final LoggedInViewModel loggedInViewModel;

    private final JLabel welcomeLabel;
    private final JButton viewAssignmentsButton;
    private final JButton createAssignmentButton;
    private final JButton logoutButton;

    public LoggedInView(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel title = new JLabel(LoggedInViewModel.TITLE_LABEL, JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        add(title, BorderLayout.NORTH);

        // Center Panel - Welcome message and role-specific buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        welcomeLabel = new JLabel("", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Student/Instructor specific buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        viewAssignmentsButton = new JButton(LoggedInViewModel.VIEW_ASSIGNMENTS_LABEL);
        viewAssignmentsButton.setPreferredSize(new Dimension(180, 40));
        buttonPanel.add(viewAssignmentsButton);

        createAssignmentButton = new JButton(LoggedInViewModel.CREATE_ASSIGNMENT_LABEL);
        createAssignmentButton.setPreferredSize(new Dimension(180, 40));
        buttonPanel.add(createAssignmentButton);

        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(buttonPanel);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel - Logout button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoutButton = new JButton(LoggedInViewModel.LOGOUT_BUTTON_LABEL);
        logoutButton.setBackground(new Color(244, 67, 54));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setPreferredSize(new Dimension(120, 35));
        bottomPanel.add(logoutButton);

        add(bottomPanel, BorderLayout.SOUTH);

        setupButtonListeners();
    }

    private void setupButtonListeners() {
        logoutButton.addActionListener(this);

        viewAssignmentsButton.addActionListener(evt -> {
            System.out.println("View Assignments clicked - To be implemented");
            JOptionPane.showMessageDialog(this,
                    "Assignment viewing functionality coming soon!",
                    "Feature Preview",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        createAssignmentButton.addActionListener(evt -> {
            System.out.println("Create Assignment clicked - To be implemented");
            JOptionPane.showMessageDialog(this,
                    "Assignment creation functionality coming soon!",
                    "Feature Preview",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(logoutButton)) {
            System.out.println("Logout clicked");
            // This would typically call a logout controller
            JOptionPane.showMessageDialog(this,
                    "Logout functionality coming soon!\nFor now, please restart the application.",
                    "Logout",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoggedInState state = (LoggedInState) evt.getNewValue();
        setFields(state);
    }

    private void setFields(LoggedInState state) {
        welcomeLabel.setText(state.getWelcomeMessage());

        // Show/hide buttons based on user role
        if ("Instructor".equals(state.getUserRole())) {
            createAssignmentButton.setVisible(true);
        } else {
            createAssignmentButton.setVisible(false);
        }
    }

    public String getViewName() {
        return viewName;
    }
}