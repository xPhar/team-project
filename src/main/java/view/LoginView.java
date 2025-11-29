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

    private final JButton logIn;
    private final JButton exit;
    private LoginController loginController = null;

    public LoginView(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Coursework Submission Platform Login");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 24));

        final JPanel textEntryPanel = new JPanel();
        textEntryPanel.setLayout(new BoxLayout(textEntryPanel, BoxLayout.Y_AXIS));
        textEntryPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel("Username"), usernameInputField);
        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel("Password"), passwordInputField);
        textEntryPanel.add(usernameInfo);
        usernameInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        textEntryPanel.add(passwordInfo);
        passwordInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        textEntryPanel.add(errorField);
        errorField.setAlignmentX(Component.CENTER_ALIGNMENT);
        textEntryPanel.add(Box.createVerticalGlue());

        textEntryPanel.setPreferredSize(new Dimension(0, 250));

        final JPanel buttons = new JPanel();
        logIn = new JButton("log in");
        buttons.add(logIn);
        exit = new JButton("Exit");
        buttons.add(exit);

        logIn.addActionListener(this);

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

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

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

        this.add(title);
        this.add(Box.createVerticalStrut(50));
        this.add(textEntryPanel);
        this.add(Box.createVerticalGlue());
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(logIn)) {
            final LoginState currentState = loginViewModel.getState();

            loginController.execute(
                    currentState.getUsername(),
                    currentState.getPassword()
            );
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