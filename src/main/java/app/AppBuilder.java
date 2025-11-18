package app;

import data_access.DummyDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import usecase.login.LoginInputBoundary;
import usecase.login.LoginInteractor;
import usecase.login.LoginOutputBoundary;
import usecase.signup.SignupInputBoundary;
import usecase.signup.SignupInteractor;
import usecase.signup.SignupOutputBoundary;
import view.LoggedInView;
import view.LoginView;
import view.SignupView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    final DummyDataAccessObject userDataAccessObject = new DummyDataAccessObject();

    private LoginView loginView;
    private LoginViewModel loginViewModel;

    private SignupView signupView;
    private SignupViewModel signupViewModel;

    private LoggedInView loggedInView;
    private LoggedInViewModel loggedInViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    public AppBuilder addLoginUsecase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(
                viewManagerModel,
                loggedInViewModel,
                signupViewModel,
                loginViewModel
        );

        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addSignupUsecase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(
                viewManagerModel,
                signupViewModel,
                loginViewModel
        );

        final SignupInputBoundary signupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary);

        SignupController signupController = new SignupController(signupInteractor);
        signupView.setSignupController(signupController);
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Coursework Submission & Grading Platform - DEMO");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set preferred size for better demo experience
        cardPanel.setPreferredSize(new Dimension(700, 550));
        application.add(cardPanel);

        // Start with login view
        viewManagerModel.setState(loginView.getViewName());
        viewManagerModel.firePropertyChange();

        application.pack();
        application.setLocationRelativeTo(null);

        // Print demo instructions
        System.out.println("=== DEMO APPLICATION STARTED ===");
        System.out.println("Available Views: Login, Signup, Logged-In Dashboard");
        System.out.println("Try these test scenarios:");
        System.out.println("1. Register as Student: username 'student1', password 'password123'");
        System.out.println("2. Register as Instructor: username 'prof1', password 'password123'");
        System.out.println("3. Try duplicate username to see error handling");
        System.out.println("4. Test validation with short passwords/usernames");
        userDataAccessObject.printAllUsers();

        return application;
    }
}
