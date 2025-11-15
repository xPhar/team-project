package app;

import data_access.DBUserDataAccessObject;

import interface_adapter.Resubmit.ResubmitController;
import interface_adapter.Resubmit.ResubmitPresenter;
import interface_adapter.Resubmit.ResubmitViewModel;
import interface_adapter.Submit.SubmitController;
import interface_adapter.Submit.SubmitPresenter;
import interface_adapter.Submit.SubmitViewModel;
import interface_adapter.ViewManagerModel;

import usecase.Resubmit.ResubmitInputBoundary;
import usecase.Resubmit.ResubmitInteractor;
import usecase.Resubmit.ResubmitOutputBoundary;
import usecase.Submit.SubmitInputBoundary;
import usecase.Submit.SubmitInteractor;
import usecase.Submit.SubmitOutputBoundary;

import view.ResubmitView;
import view.SubmitView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final Session session =  new Session();
    // TODO: UserFactory

    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // TODO: Add instance variables

    private SubmitView submitView;
    private ResubmitView resubmitView;
    private SubmitViewModel submitViewModel;
    private ResubmitViewModel resubmitViewModel;

    private final DBUserDataAccessObject userDataAccessObject =  new DBUserDataAccessObject();

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
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

    public AppBuilder addSubmitUseCase() {
        final SubmitOutputBoundary submitOutputBoundary = new SubmitPresenter(submitViewModel);
        final SubmitInputBoundary submitInteractor = new SubmitInteractor(
                userDataAccessObject, submitOutputBoundary, session);

        SubmitController submitController = new SubmitController(submitInteractor);
        submitView.setSubmitController(submitController);
        return this;
    }

    public AppBuilder addResubmitUseCase() {
        final ResubmitOutputBoundary resubmitOutputBoundary = new ResubmitPresenter(
                viewManagerModel, resubmitViewModel, submitViewModel
        );
        final ResubmitInputBoundary resubmitInteractor = new ResubmitInteractor(resubmitOutputBoundary, session);
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
     */
}
