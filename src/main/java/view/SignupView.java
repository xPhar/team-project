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
 * The View for when the user is logging in to the program.
 */
public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "signup";
    private final SignupViewModel signupViewModel;

    private final JTextField usernameInputField = new JTextField(24);
    private final JLabel usernameErrorField = new JLabel();

    private final JPasswordField passwordInputField = new JPasswordField(24);
    private final JLabel passwordErrorField = new JLabel();

    private final JButton register;
    private final JButton cancel;
    private SignupController signupController = null;

    public SignupView(SignupViewModel signupViewModel) {

        this.signupViewModel = signupViewModel;
        this.signupViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Signup Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel("Username"), usernameInputField);
        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel("Password"), passwordInputField);

        final JPanel buttons = new JPanel();
        register = new JButton("Register");
        buttons.add(register);
        cancel = new JButton("cancel");
        buttons.add(cancel);

        register.addActionListener(
                evt -> {
                    if (evt.getSource().equals(register)) {
                        final SignupState currentState = signupViewModel.getState();

                        signupController.execute(
                                currentState.getUsername(),
                                currentState.getPassword()
                        );
                    }
                }
        );

        cancel.addActionListener(this);

        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                signupViewModel.setState(currentState);
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
                final SignupState currentState = signupViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                signupViewModel.setState(currentState);
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
        this.add(usernameInfo);
        this.add(usernameErrorField);
        this.add(passwordInfo);
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SignupState state = (SignupState) evt.getNewValue();
        setFields(state);
        usernameErrorField.setText(state.getSignupError());
    }

    private void setFields(SignupState state) {
        usernameInputField.setText(state.getUsername());
    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController signupController) {
        this.signupController = signupController;
    }
}