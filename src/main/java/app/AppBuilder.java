package app;

import data_access.DummyDataAccessObject;
import interface_adapter.ViewManagerModel;
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
import view.LoginView;
import view.SignupView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    // TODO: Add instance variables
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    final DummyDataAccessObject userDataAccessObject = new DummyDataAccessObject();

    private LoginView loginView;
    private LoginViewModel loginViewModel;

    private SignupView signupView;
    private SignupViewModel signupViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    // TODO: Implement builder methods

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



    public AppBuilder addLoginUsecase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addSignupUsecase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel);
        final SignupInputBoundary signupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary);

        SignupController signupController = new SignupController(signupInteractor);
        signupView.setSignupController(signupController);
        return this;
    }

    // TODO: Update builder once full login flow is complete
    public JFrame build() {
        final JFrame application = new JFrame("User Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(loginView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }
}