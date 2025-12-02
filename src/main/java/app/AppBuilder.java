package app;

import data_access.FacadeDAO;

import interface_adapter.Assignments.*;
import interface_adapter.CreateAssignment.*;
import interface_adapter.EditAssignment.*;
import interface_adapter.Resubmit.*;
import interface_adapter.Submit.*;
import interface_adapter.logged_in.*;
import interface_adapter.login.*;
import interface_adapter.signup.*;
import interface_adapter.submission.*;
import interface_adapter.submission_list.*;
import interface_adapter.class_average.*;
import interface_adapter.ViewManagerModel;

import usecase.Assignments.*;
import usecase.CreateAssignment.*;
import usecase.EditAssignment.*;
import usecase.Resubmit.*;
import usecase.Submit.*;
import usecase.logged_in.*;
import usecase.login.*;
import usecase.signup.*;
import usecase.Grade.*;
import usecase.Submission.*;
import usecase.SubmissionList.*;
import usecase.class_average.*;

import view.*;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private final FacadeDAO userDataAccessObject = new FacadeDAO();

    private LoginView loginView;
    private LoginViewModel loginViewModel;
    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoggedInView loggedInView;
    private LoggedInViewModel loggedInViewModel;
    private SubmitView submitView;
    private SubmitViewModel submitViewModel;
    private ResubmitView resubmitView;
    private ResubmitViewModel resubmitViewModel;
    private ClassAverageView classAverageView;
    private ClassAverageViewModel classAverageViewModel;

    // Assignment-related views and view models
    private AssignmentView assignmentView;
    private AssignmentsViewModel assignmentsViewModel;
    private CreateAssignmentView createAssignmentView;
    private CreateAssignmentViewModel createAssignmentViewModel;
    private EditAssignmentView editAssignmentView;
    private EditAssignmentViewModel editAssignmentViewModel;

    // Submission-related views
    private SubmissionListView submissionListView;
    private SubmissionListViewModel submissionListViewModel;
    private SubmissionView submissionView;
    private SubmissionViewModel submissionViewModel;

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

    public AppBuilder addClassAverageView() {
        classAverageViewModel = new ClassAverageViewModel();
        classAverageView = new ClassAverageView(classAverageViewModel);
        cardPanel.add(classAverageView, classAverageView.getViewName());
        return this;
    }

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

    public AppBuilder addAssignmentView() {
        assignmentsViewModel = new AssignmentsViewModel();
        assignmentView = new AssignmentView(assignmentsViewModel);
        cardPanel.add(assignmentView, assignmentView.getViewName());
        return this;
    }

    public AppBuilder addCreateAssignmentView() {
        createAssignmentViewModel = new CreateAssignmentViewModel();
        createAssignmentView = new CreateAssignmentView(createAssignmentViewModel);
        cardPanel.add(createAssignmentView, createAssignmentView.getViewName());
        return this;
    }

    public AppBuilder addEditAssignmentView() {
        editAssignmentViewModel = new EditAssignmentViewModel();
        editAssignmentView = new EditAssignmentView(editAssignmentViewModel);
        cardPanel.add(editAssignmentView, editAssignmentView.getViewName());
        return this;
    }

    // --- Use Case wiring ---
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(
                viewManagerModel, loggedInViewModel, assignmentsViewModel, signupViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);
        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addSignupUseCase() {
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

    public AppBuilder addLoggedInUseCase() {
        final LoggedInOutputBoundary loggedInOutputBoundary = new LoggedInPresenter(
                viewManagerModel,
                loggedInViewModel,
                loginViewModel,
                submitViewModel,
                resubmitViewModel,
                submissionListViewModel,
                classAverageViewModel,
                createAssignmentViewModel);
        final LoggedInInputBoundary loggedInInteractor = new LoggedInInteractor(
                userDataAccessObject, loggedInOutputBoundary);

        LoggedInController loggedInController = new LoggedInController(loggedInInteractor);
        loggedInView.setLoggedInController(loggedInController);
        return this;
    }

    public AppBuilder addSubmitUseCase() {
        final SubmitOutputBoundary submitOutputBoundary = new SubmitPresenter(
                viewManagerModel, submitViewModel, loggedInViewModel);
        final SubmitInputBoundary submitInteractor = new SubmitInteractor(
                userDataAccessObject, submitOutputBoundary);

        SubmitController submitController = new SubmitController(submitInteractor);
        submitView.setSubmitController(submitController);
        return this;
    }

    public AppBuilder addResubmitUseCase() {
        final ResubmitOutputBoundary resubmitOutputBoundary = new ResubmitPresenter(
                viewManagerModel, resubmitViewModel, submitViewModel, loggedInViewModel);
        final ResubmitInputBoundary resubmitInteractor = new ResubmitInteractor(resubmitOutputBoundary,
                userDataAccessObject);
        ResubmitController resubmitController = new ResubmitController(resubmitInteractor);
        resubmitView.setResubmitController(resubmitController);
        return this;
    }

    public AppBuilder addClassAverageUseCase() {
        final ClassAverageOutputBoundary classAverageOutputBoundary = new ClassAveragePresenter(
                classAverageViewModel, loggedInViewModel, viewManagerModel
        );
        final ClassAverageInputBoundary classAverageInteractor = new  ClassAverageInteractor(
                userDataAccessObject, classAverageOutputBoundary
        );
        ClassAverageController classAverageController = new ClassAverageController(classAverageInteractor);
        classAverageView.setClassAverageController(classAverageController);
        return this;
    }

    public AppBuilder addSubmissionListUseCase() {
        final SubmissionListOutputBoundary submissionListOutputBoundary = new SubmissionListPresenter(
                submissionListViewModel, viewManagerModel,
                submissionViewModel, assignmentsViewModel);
        SubmissionListInputBoundary submissionListInputBoundary = new SubmissionListInteractor(
                submissionListOutputBoundary, userDataAccessObject);

        SubmissionListController submissionListController = new SubmissionListController(submissionListInputBoundary);
        submissionListView.setSubmissionListController(submissionListController);
        return this;
    }

    public AppBuilder addSubmissionUseCase() {
        final SubmissionPresenter presenter = new SubmissionPresenter(submissionViewModel,
                viewManagerModel, submissionListViewModel);
        final SubmissionInputBoundary submissionInputBoundary = new SubmissionInteractor(presenter, userDataAccessObject);
        final GradeInputBoundary gradeInputBoundary = new GradeInteractor(
                userDataAccessObject,
                presenter);

        SubmissionController submissionController = new SubmissionController(submissionInputBoundary,
                gradeInputBoundary);
        submissionView.setSubmissionController(submissionController);
        return this;
    }

    public AppBuilder addAssignmentsUseCase() {
        final AssignmentsOutputBoundary assignmentsOutputBoundary = new AssignmentsPresenter(
                assignmentsViewModel, viewManagerModel, submissionListViewModel);
        final AssignmentsInputBoundary assignmentsInteractor = new AssignmentsInteractor(
                userDataAccessObject, assignmentsOutputBoundary);

        AssignmentsController assignmentsController = new AssignmentsController(assignmentsInteractor);
        assignmentView.setAssignmentsController(assignmentsController);
        return this;
    }

    public AppBuilder addCreateAssignmentUseCase() {
        final CreateAssignmentOutputBoundary createAssignmentOutputBoundary = new CreateAssignmentPresenter(
                createAssignmentViewModel, assignmentsViewModel, viewManagerModel);

        final CreateAssignmentInputBoundary createAssignmentInteractor = new CreateAssignmentInteractor(
                userDataAccessObject, createAssignmentOutputBoundary);

        CreateAssignmentController createAssignmentController = new CreateAssignmentController(
                createAssignmentInteractor);
        createAssignmentView.setCreateAssignmentController(createAssignmentController);
        return this;
    }

    public AppBuilder addEditAssignmentUseCase() {
        final EditAssignmentOutputBoundary editAssignmentOutputBoundary = new EditAssignmentPresenter(
                editAssignmentViewModel, assignmentsViewModel, viewManagerModel);

        final EditAssignmentInputBoundary editAssignmentInteractor = new EditAssignmentInteractor(
                userDataAccessObject, editAssignmentOutputBoundary);

        EditAssignmentController editAssignmentController = new EditAssignmentController(
                editAssignmentInteractor, viewManagerModel, assignmentsViewModel, editAssignmentViewModel);
        editAssignmentView.setEditAssignmentController(editAssignmentController);
        assignmentView.setEditAssignmentController(editAssignmentController);
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Coursework Submission Platform");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.setPreferredSize(new Dimension(1280, 720));

        application.add(cardPanel);

        viewManagerModel.setState(loginView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }
}
