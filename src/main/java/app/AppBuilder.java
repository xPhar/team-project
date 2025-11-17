package app;

import interface_adapter.Submit.SubmitViewModel;
import interface_adapter.ViewManagerModel;
import view.SubmitView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    // TODO: UserFactory
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // TODO: Add instance variables

    private SubmitView submitView;
    private SubmitViewModel submitViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addSubmitView() {
        submitViewModel = new SubmitViewModel();
        submitView = new SubmitView(submitViewModel);
        cardPanel.add(submitView, submitView.getViewName());
        return this;
    }
    // TODO: Implement builder methods

    public JFrame build() {
        final JFrame application = new JFrame("User Login Example");
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
     */
}
