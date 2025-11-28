package view;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for user registration.
 */
public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "signup";
    private final SignupViewModel signupViewModel;
    private boolean updatingFromModel = false;

    // Form fields
    private final JTextField fullNameInputField = new JTextField(20);
    private final JTextField usernameInputField = new JTextField(20);
    private final JPasswordField passwordInputField = new JPasswordField(20);
    private final JPasswordField confirmPasswordField = new JPasswordField(20);
    private final JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"Student", "Instructor"});

    // Error labels
    private final JLabel fullNameErrorField = new JLabel();
    private final JLabel usernameErrorField = new JLabel();
    private final JLabel passwordErrorField = new JLabel();
    private final JLabel confirmPasswordErrorField = new JLabel();
    private final JLabel generalErrorField = new JLabel();

    // Buttons - REMOVED FINAL KEYWORD
    private JButton registerButton;
    private JButton cancelButton;
    private SignupController signupController;

    public SignupView(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
        this.signupViewModel.addPropertyChangeListener(this);

        setupUI();
        setupListeners();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(242, 246, 255));
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JPanel card = new JPanel(new BorderLayout(14, 14));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 218, 235), 1, true),
                BorderFactory.createEmptyBorder(20, 24, 24, 24)));
        card.setBackground(Color.WHITE);

        JLabel title = new JLabel("Create Your Account", JLabel.CENTER);
        title.setFont(new Font("Inter", Font.BOLD, 22));
        title.setForeground(new Color(38, 50, 74));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        card.add(title, BorderLayout.NORTH);

        JPanel formPanel = createFormPanel();
        card.add(formPanel, BorderLayout.CENTER);

        // Buttons and error panel
        JPanel bottomPanel = createBottomPanel();
        card.add(bottomPanel, BorderLayout.SOUTH);

        add(card, BorderLayout.CENTER);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Registration Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Full Name
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Full Name:*"), gbc);
        gbc.gridx = 1;
        formPanel.add(fullNameInputField, gbc);
        gbc.gridx = 2;
        fullNameErrorField.setForeground(Color.RED);
        fullNameErrorField.setPreferredSize(new Dimension(200, 20));
        formPanel.add(fullNameErrorField, gbc);

        // Username
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Username:*"), gbc);
        gbc.gridx = 1;
        formPanel.add(usernameInputField, gbc);
        gbc.gridx = 2;
        usernameErrorField.setForeground(Color.RED);
        usernameErrorField.setPreferredSize(new Dimension(200, 20));
        formPanel.add(usernameErrorField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Password:*"), gbc);
        gbc.gridx = 1;
        formPanel.add(passwordInputField, gbc);
        gbc.gridx = 2;
        passwordErrorField.setForeground(Color.RED);
        passwordErrorField.setPreferredSize(new Dimension(200, 20));
        formPanel.add(passwordErrorField, gbc);

        // Confirm Password
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Confirm Password:*"), gbc);
        gbc.gridx = 1;
        formPanel.add(confirmPasswordField, gbc);
        gbc.gridx = 2;
        confirmPasswordErrorField.setForeground(Color.RED);
        confirmPasswordErrorField.setPreferredSize(new Dimension(200, 20));
        formPanel.add(confirmPasswordErrorField, gbc);

        // Role
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Account Type:*"), gbc);
        gbc.gridx = 1;
        formPanel.add(roleComboBox, gbc);

        return formPanel;
    }

    private JPanel createBottomPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        // INITIALIZE BUTTONS HERE INSTEAD OF DECLARING THEM FINAL
        registerButton = new JButton("Register");
        cancelButton = new JButton("Cancel");

        // Style buttons
        registerButton.setBackground(new Color(75, 123, 236));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setFont(registerButton.getFont().deriveFont(Font.BOLD));
        registerButton.setOpaque(true);
        registerButton.setBorderPainted(false);
        cancelButton.setBackground(new Color(230, 234, 243));
        cancelButton.setForeground(new Color(51, 63, 87));
        cancelButton.setFocusPainted(false);
        cancelButton.setFont(cancelButton.getFont().deriveFont(Font.BOLD));
        cancelButton.setOpaque(true);
        cancelButton.setBorderPainted(false);

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        // General error message
        generalErrorField.setForeground(Color.RED);
        generalErrorField.setHorizontalAlignment(SwingConstants.CENTER);
        generalErrorField.setFont(generalErrorField.getFont().deriveFont(Font.ITALIC, 12f));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(generalErrorField, BorderLayout.SOUTH);

        return bottomPanel;
    }

    private void setupListeners() {
        registerButton.addActionListener(evt -> {
            SignupState currentState = signupViewModel.getState();

            // Clear previous errors
            clearErrors();

            // Validate form
            if (!validateForm(currentState)) {
                return;
            }

            // Call controller
            signupController.execute(
                    currentState.getUsername(),
                    currentState.getPassword(),
                    currentState.getUserRole(),
                    currentState.getFullName()
            );
        });

        cancelButton.addActionListener(evt -> {
            if (signupController != null) {
                signupController.cancel();
            }
        });

        // Document listeners for real-time validation
        addDocumentListener(fullNameInputField, "fullName");
        addDocumentListener(usernameInputField, "username");
        addDocumentListener(passwordInputField, "password");
        addDocumentListener(confirmPasswordField, "confirmPassword");

        roleComboBox.addActionListener(e -> {
            SignupState currentState = signupViewModel.getState();
            currentState.setUserRole((String) roleComboBox.getSelectedItem());
            signupViewModel.setState(currentState);
        });
    }

    private void addDocumentListener(JTextField field, String fieldType) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                if (updatingFromModel) {
                    return;
                }
                SignupState currentState = signupViewModel.getState();
                String text = field.getText();

                switch (fieldType) {
                    case "fullName":
                        currentState.setFullName(text);
                        validateFullName(text);
                        break;
                    case "username":
                        currentState.setUsername(text);
                        validateUsername(text);
                        break;
                    case "password":
                        currentState.setPassword(text);
                        validatePassword(text);
                        break;
                    case "confirmPassword":
                        validateConfirmPassword(text);
                        break;
                }
                signupViewModel.setState(currentState);
            }

            public void insertUpdate(DocumentEvent e) { update(); }
            public void removeUpdate(DocumentEvent e) { update(); }
            public void changedUpdate(DocumentEvent e) { update(); }
        });
    }

    private boolean validateForm(SignupState state) {
        boolean isValid = true;

        if (state.getFullName() == null || state.getFullName().trim().isEmpty()) {
            fullNameErrorField.setText("Full name is required");
            isValid = false;
        }

        if (state.getUsername() == null || state.getUsername().trim().isEmpty()) {
            usernameErrorField.setText("Username is required");
            isValid = false;
        } else if (state.getUsername().length() < 3) {
            usernameErrorField.setText("Username must be at least 3 characters");
            isValid = false;
        }

        if (state.getPassword() == null || state.getPassword().isEmpty()) {
            passwordErrorField.setText("Password is required");
            isValid = false;
        } else if (state.getPassword().length() < 6) {
            passwordErrorField.setText("Password must be at least 6 characters");
            isValid = false;
        }

        String confirmPassword = new String(confirmPasswordField.getPassword());
        if (!state.getPassword().equals(confirmPassword)) {
            confirmPasswordErrorField.setText("Passwords do not match");
            isValid = false;
        }

        return isValid;
    }

    private void validateFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            fullNameErrorField.setText("Full name is required");
        } else {
            fullNameErrorField.setText("");
        }
    }

    private void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            usernameErrorField.setText("Username is required");
        } else if (username.length() < 3) {
            usernameErrorField.setText("Username must be at least 3 characters");
        } else {
            usernameErrorField.setText("");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            passwordErrorField.setText("Password is required");
        } else if (password.length() < 6) {
            passwordErrorField.setText("Password must be at least 6 characters");
        } else {
            passwordErrorField.setText("");
        }

        validateConfirmPassword(new String(confirmPasswordField.getPassword()));
    }

    private void validateConfirmPassword(String confirmPassword) {
        String password = new String(passwordInputField.getPassword());
        if (!password.equals(confirmPassword)) {
            confirmPasswordErrorField.setText("Passwords do not match");
        } else {
            confirmPasswordErrorField.setText("");
        }
    }

    private void clearErrors() {
        fullNameErrorField.setText("");
        usernameErrorField.setText("");
        passwordErrorField.setText("");
        confirmPasswordErrorField.setText("");
        generalErrorField.setText("");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SignupState state = (SignupState) evt.getNewValue();
        updatingFromModel = true;
        setFields(state);
        updatingFromModel = false;
        if (state.getSignupError() != null && !state.getSignupError().isEmpty()) {
            generalErrorField.setText(state.getSignupError());
        }
    }

    private void setFields(SignupState state) {
        if (!fullNameInputField.getText().equals(state.getFullName())) {
            fullNameInputField.setText(state.getFullName());
        }
        if (!usernameInputField.getText().equals(state.getUsername())) {
            usernameInputField.setText(state.getUsername());
        }
        if (roleComboBox.getSelectedItem() != state.getUserRole()) {
            roleComboBox.setSelectedItem(state.getUserRole());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController controller) {
        this.signupController = controller;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Action: " + evt.getActionCommand());
    }
}
