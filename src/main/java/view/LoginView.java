package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jetbrains.annotations.NotNull;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

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
    private LoginController loginController;

    public LoginView(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBackground(new Color(242, 246, 255));
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        addViewElements();
    }

    private void addViewElements() {
        final JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setAlignmentY(Component.CENTER_ALIGNMENT);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 218, 235), 1, true),
                BorderFactory.createEmptyBorder(24, 24, 24, 24)));

        final JLabel title = initTitle();

        final JPanel textEntryPanel = initTextEntryPanel();

        final JPanel buttons = initButtons();

        final JPanel focusPanel = initFocusPanel(title, textEntryPanel, buttons);

        card.add(focusPanel);
        add(card, BorderLayout.CENTER);
    }

    @NotNull
    private JPanel initFocusPanel(JLabel title, JPanel textEntryPanel, JPanel buttons) {
        final JPanel focusPanel = new JPanel();
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
        return focusPanel;
    }

    @NotNull
    private JPanel initButtons() {
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
        return buttons;
    }

    @NotNull
    private JPanel initTextEntryPanel() {
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

        usernameInputField.getDocument().addDocumentListener(usernameListener());

        passwordInputField.getDocument().addDocumentListener(passwordListener());

        return textEntryPanel;
    }

    @NotNull
    private JLabel initTitle() {
        final JLabel title = new JLabel("Coursework Submission Platform");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Inter", Font.BOLD, 32));
        title.setForeground(new Color(38, 50, 74));
        return title;
    }

    private DocumentListener usernameListener() {
        return new DocumentListener() {
            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                loginViewModel.setState(currentState);
            }

            public void insertUpdate(DocumentEvent event) {
                documentListenerHelper();
            }

            public void removeUpdate(DocumentEvent event) {
                documentListenerHelper();
            }

            public void changedUpdate(DocumentEvent event) {
                documentListenerHelper();
            }
        };
    }

    private DocumentListener passwordListener() {
        return new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                loginViewModel.setState(currentState);
            }

            public void insertUpdate(DocumentEvent event) {
                documentListenerHelper();
            }

            public void removeUpdate(DocumentEvent event) {
                documentListenerHelper();
            }

            public void changedUpdate(DocumentEvent event) {
                documentListenerHelper();
            }
        };
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
