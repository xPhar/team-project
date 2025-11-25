package app;

import data_access.FacadeDAO;
import data_access.FakeUserDataAccessObject;
import data_access.TestDAO;

import entity.User;
import interface_adapter.Resubmit.*;
import interface_adapter.Submit.*;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.*;
import interface_adapter.submission.*;
import interface_adapter.submission_list.*;
import interface_adapter.ViewManagerModel;

import org.json.JSONObject;
import usecase.Resubmit.*;
import usecase.Submit.*;
import usecase.login.*;
import usecase.Grade.*;
import usecase.Submission.*;
import usecase.SubmissionList.*;

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

    private LoginView loginView;
    private LoginViewModel loginViewModel;
    private LoggedInView loggedInView;
    private LoggedInViewModel loggedInViewModel;
    private SubmitView submitView;
    private SubmitViewModel submitViewModel;
    private ResubmitView resubmitView;
    private ResubmitViewModel resubmitViewModel;

    //private final FakeUserDataAccessObject userDataAccessObject =  new FakeUserDataAccessObject(false, false);
    private final FacadeDAO userDataAccessObject = new FacadeDAO();

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
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
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(
                viewManagerModel, loggedInViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);
        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addLoggedInUseCase() {
        // TODO: Implement
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
        // Test user credentials: username: testUser123, password: password

        final JFrame application = new JFrame("This is a title");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(loginView.getViewName());
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
     */
}
