package app;

import data_access.TestDAO;
import interface_adapter.Submit.SubmitViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.submission.SubmissionController;
import interface_adapter.submission.SubmissionPresenter;
import interface_adapter.submission.SubmissionViewModel;
import interface_adapter.submission_list.SubmissionListController;
import interface_adapter.submission_list.SubmissionListPresenter;
import interface_adapter.submission_list.SubmissionListViewModel;
import usecase.Grade.GradeInputBoundary;
import usecase.Grade.GradeInteractor;
import usecase.Submission.SubmissionInputBoundary;
import usecase.Submission.SubmissionInteractor;
import usecase.Submission.SubmissionOutputBoundary;
import usecase.SubmissionList.SubmissionListInputBoundary;
import usecase.SubmissionList.SubmissionListInteractor;
import usecase.SubmissionList.SubmissionListOutputBoundary;
import view.SubmissionListView;
import view.SubmissionView;
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

    final TestDAO testDAO = new TestDAO();

    // TODO: Add instance variables

    private SubmitView submitView;
    private SubmitViewModel submitViewModel;

    private SubmissionListViewModel submissionListViewModel;
    private SubmissionListView submissionListView;
    private SubmissionViewModel submissionViewModel;
    private SubmissionView submissionView;

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

    public AppBuilder addSubmissionListView() {
        submissionListViewModel = new SubmissionListViewModel();
        submissionListView = new SubmissionListView(submissionListViewModel);
        cardPanel.add(submissionListView, submissionListView.getViewName());
        return this;
    }

    public AppBuilder addSubmissionView() {
        submissionViewModel = new SubmissionViewModel();
        submissionView = new SubmissionView(submissionViewModel);
        cardPanel.add(submissionView, submissionView.getViewName());
        return this;
    }

    public AppBuilder addSubmissionListUseCase() {
        final SubmissionListOutputBoundary submissionListOutputBoundary =
                new SubmissionListPresenter(submissionListViewModel, viewManagerModel,
                        submissionViewModel);
        SubmissionListInputBoundary submissionListInputBoundary =
                new SubmissionListInteractor(submissionListOutputBoundary, testDAO);

        SubmissionListController submissionListController =
                new SubmissionListController(submissionListInputBoundary);
        submissionListView.setSubmissionListController(submissionListController);
        return this;
    }

    public AppBuilder addSubmissionUseCase() {
        final SubmissionPresenter presenter = new SubmissionPresenter(submissionViewModel,
                viewManagerModel, submissionListViewModel);
        final SubmissionInputBoundary submissionInputBoundary =
                new SubmissionInteractor(presenter);
        final GradeInputBoundary gradeInputBoundary = new GradeInteractor(
                testDAO,
                presenter
        );

        SubmissionController submissionController =
                new SubmissionController(submissionInputBoundary, gradeInputBoundary);
        submissionView.setSubmissionController(submissionController);
        return this;
    }

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
