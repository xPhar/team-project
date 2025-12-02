package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for when the user is logging in to the program.
 */
public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "login";
    private final LoginViewModel loginViewModel;

    private final JTextField usernameInputField = new JTextField(24);

    private final JPasswordField passwordInputField = new JPasswordField(24);
    private final JLabel errorField = new JLabel();

    private final JButton login = new JButton("Log in");
    private final JButton register = new JButton("Register");
    private final JButton exit = new JButton("Exit");
    private LoginController loginController = null;

    public LoginView(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBackground(new Color(242, 246, 255));
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setAlignmentY(Component.CENTER_ALIGNMENT);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 218, 235), 1, true),
                BorderFactory.createEmptyBorder(24, 24, 24, 24)));

        final JLabel title = new JLabel("Coursework Submission Platform");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Inter", Font.BOLD, 32));
        title.setForeground(new Color(38, 50, 74));

        final JPanel textEntryPanel = new JPanel();
        textEntryPanel.setOpaque(false);
        textEntryPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel("Username "), usernameInputField);
        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel(" Password "), passwordInputField);
        errorField.setForeground(Color.RED);
        textEntryPanel.add(usernameInfo);
        textEntryPanel.add(Box.createVerticalStrut(8));
        textEntryPanel.add(passwordInfo);
        textEntryPanel.add(errorField);

        final JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttons.setOpaque(false);
        stylePrimaryButton(login);
        styleSecondaryButton(register);
        styleGhostButton(exit);
        buttons.add(login);
        buttons.add(register);
        buttons.add(exit);

        login.addActionListener(this);

        register.addActionListener(this);

        exit.addActionListener(this);

        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        JPanel focusPanel = new JPanel();
        focusPanel.setLayout(new BoxLayout(focusPanel, BoxLayout.Y_AXIS));
        focusPanel.setBackground(Color.LIGHT_GRAY);
        focusPanel.setPreferredSize(new Dimension(600, 540));
        focusPanel.setMaximumSize(new Dimension(600, 540));

        focusPanel.add(Box.createVerticalStrut(24));
        focusPanel.add(title);
        focusPanel.add(Box.createVerticalStrut(128));
        focusPanel.add(textEntryPanel);
        focusPanel.add(Box.createVerticalGlue());
        focusPanel.add(buttons);

        card.add(focusPanel);
        add(card, BorderLayout.CENTER);
    }

    private void stylePrimaryButton(JButton button) {
        button.setBackground(new Color(75, 123, 236));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(button.getFont().deriveFont(Font.BOLD));
        button.setPreferredSize(new Dimension(110, 32));
        button.setOpaque(true);
        button.setBorderPainted(false);
    }

    private void styleSecondaryButton(JButton button) {
        button.setBackground(new Color(230, 234, 243));
        button.setForeground(new Color(51, 63, 87));
        button.setFocusPainted(false);
        button.setFont(button.getFont().deriveFont(Font.BOLD));
        button.setPreferredSize(new Dimension(110, 32));
        button.setOpaque(true);
        button.setBorderPainted(false);
    }

    private void styleGhostButton(JButton button) {
        button.setBackground(new Color(247, 249, 252));
        button.setForeground(new Color(51, 63, 87));
        button.setFocusPainted(false);
        button.setFont(button.getFont().deriveFont(Font.BOLD));
        button.setPreferredSize(new Dimension(110, 32));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(login)) {
            final LoginState currentState = loginViewModel.getState();

            loginController.execute(
                    currentState.getUsername(),
                    currentState.getPassword()
            );
        }
        else if (evt.getSource().equals(register)) {
            loginController.switchToSignupView();
        }
        else if (evt.getSource().equals(exit)) {
            // Close program
            if (this.getParent() instanceof JFrame frame) {
                frame.dispose();
            }
            System.exit(0);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = (LoginState) evt.getNewValue();
        setFields(state);
        errorField.setText(state.getLoginError());
    }

    private void setFields(LoginState state) {
        usernameInputField.setText(state.getUsername());
        passwordInputField.setText(state.getPassword());
    }

    public String getViewName() {
        return viewName;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}
