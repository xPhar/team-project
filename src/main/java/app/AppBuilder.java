package app;

import data_access.DummyDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import usecase.login.LoginInputBoundary;
import usecase.login.LoginInteractor;
import usecase.login.LoginOutputBoundary;
import usecase.signup.SignupInputBoundary;
import usecase.signup.SignupInteractor;
import usecase.signup.SignupOutputBoundary;
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
        // We'll create this later, but adding the structure for now
        loggedInViewModel = new LoggedInViewModel();
        // loggedInView = new LoggedInView(loggedInViewModel);
        // cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    public AppBuilder addLoginUsecase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(
                viewManagerModel,
                loggedInViewModel,  // We'll create this
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
        // Fix: SignupPresenter now requires LoginViewModel as third parameter
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(
                viewManagerModel,
                signupViewModel,
                loginViewModel  // Added this parameter
        );

        final SignupInputBoundary signupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary);

        SignupController signupController = new SignupController(signupInteractor);
        signupView.setSignupController(signupController);
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Coursework Submission & Grading Platform");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set preferred size for better initial window size
        cardPanel.setPreferredSize(new Dimension(600, 500));
        application.add(cardPanel);

        // Start with login view
        viewManagerModel.setState(loginView.getViewName());
        viewManagerModel.firePropertyChange();

        application.pack();
        application.setLocationRelativeTo(null); // Center the window

        // Print initial state for debugging
        System.out.println("Application started successfully!");
        System.out.println("Available views: Login, Signup");
        userDataAccessObject.printAllUsers();

        return application;
    }
}