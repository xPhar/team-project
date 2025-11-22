package app;

import data_access.FakeUserDataAccessObject;

import interface_adapter.Resubmit.*;
import interface_adapter.Submit.*;
import interface_adapter.login.*;
import interface_adapter.ViewManagerModel;

import usecase.Resubmit.*;
import usecase.Submit.*;
import usecase.login.*;

import view.*;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    // TODO: UserFactory

    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private LoginView loginView;
    private LoginViewModel loginViewModel;
    private SubmitView submitView;
    private ResubmitView resubmitView;
    private SubmitViewModel submitViewModel;
    private ResubmitViewModel resubmitViewModel;

    private final FakeUserDataAccessObject userDataAccessObject =  new FakeUserDataAccessObject();

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addSubmitView() {
        submitViewModel = new SubmitViewModel();
        submitView = new SubmitView(submitViewModel);
        cardPanel.add(submitView, submitView.getViewName());
        return this;
    }

    public AppBuilder addResubmitView() {
        resubmitViewModel = new ResubmitViewModel();
        resubmitView = new ResubmitView(resubmitViewModel);
        cardPanel.add(resubmitView, resubmitView.getViewName());
        return this;
    }

    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);
        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addSubmitUseCase() {
        final SubmitOutputBoundary submitOutputBoundary = new SubmitPresenter(submitViewModel);
        final SubmitInputBoundary submitInteractor = new SubmitInteractor(
                userDataAccessObject, submitOutputBoundary);

        SubmitController submitController = new SubmitController(submitInteractor);
        submitView.setSubmitController(submitController);
        return this;
    }

    public AppBuilder addResubmitUseCase() {
        final ResubmitOutputBoundary resubmitOutputBoundary = new ResubmitPresenter(
                viewManagerModel, resubmitViewModel, submitViewModel
        );
        final ResubmitInputBoundary resubmitInteractor = new ResubmitInteractor(resubmitOutputBoundary);
        ResubmitController resubmitController = new ResubmitController(resubmitInteractor);
        resubmitView.setResubmitController(resubmitController);
        return this;
    }
    // TODO: Implement builder methods

    public JFrame build() {
        final JFrame application = new JFrame("This is a title");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(submitView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }
    // Here's an example method from the CA Lab:
    /*
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
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
