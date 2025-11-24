package app;

import data_access.FakeUserDataAccessObject;

import data_access.TestDAO;
import interface_adapter.Resubmit.ResubmitController;
import interface_adapter.Resubmit.ResubmitPresenter;
import interface_adapter.Resubmit.ResubmitViewModel;
import interface_adapter.Submit.SubmitController;
import interface_adapter.Submit.SubmitPresenter;
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
import usecase.Resubmit.ResubmitInputBoundary;
import usecase.Resubmit.ResubmitInteractor;
import usecase.Resubmit.ResubmitOutputBoundary;
import usecase.Submission.SubmissionInputBoundary;
import usecase.Submission.SubmissionInteractor;
import usecase.SubmissionList.SubmissionListInputBoundary;
import usecase.SubmissionList.SubmissionListInteractor;
import usecase.SubmissionList.SubmissionListOutputBoundary;
import usecase.Submit.SubmitInputBoundary;
import usecase.Submit.SubmitInteractor;
import usecase.Submit.SubmitOutputBoundary;

import view.*;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    // TODO: UserFactory

    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);
    //If we need to switch View, just write viewManagerModel.setstate(viewName), where viewName is a String

    // TODO: Add instance variables

    private SubmitView submitView;
    private ResubmitView resubmitView;
    private SubmitViewModel submitViewModel;
    private ResubmitViewModel resubmitViewModel;

    private final FakeUserDataAccessObject userDataAccessObject =  new FakeUserDataAccessObject(false, false);

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
                userDataAccessObject, submitOutputBoundary);

        SubmitController submitController = new SubmitController(submitInteractor);
        submitView.setSubmitController(submitController);
        return this;
    }

    public AppBuilder addResubmitUseCase() {
        final ResubmitOutputBoundary resubmitOutputBoundary = new ResubmitPresenter(
                viewManagerModel, resubmitViewModel, submitViewModel
        );
        final ResubmitInputBoundary resubmitInteractor = new ResubmitInteractor(resubmitOutputBoundary,
                                                                                userDataAccessObject);
        ResubmitController resubmitController = new ResubmitController(resubmitInteractor);
        resubmitView.setResubmitController(resubmitController);
        return this;
    }

    // Mark Assignment use case
    private SubmissionListView submissionListView;
    private SubmissionListViewModel submissionListViewModel;
    private SubmissionView submissionView;
    private SubmissionViewModel submissionViewModel;
    private final TestDAO testDAO = new TestDAO();

    public AppBuilder addSubmissionListView() {
        submissionListViewModel = new SubmissionListViewModel();
        submissionListView = new SubmissionListView(submissionListViewModel);
        cardPanel.add(submissionListView, submissionListView.getViewName());
        return this;
    }
    public AppBuilder addSubmissionView() {
        submissionViewModel = new  SubmissionViewModel();
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
                new SubmissionInteractor(presenter, testDAO);
        final GradeInputBoundary gradeInputBoundary = new GradeInteractor(
                testDAO,
                presenter
        );

        SubmissionController submissionController =
                new SubmissionController(submissionInputBoundary, gradeInputBoundary);
        submissionView.setSubmissionController(submissionController);
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
