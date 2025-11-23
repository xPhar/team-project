package app;

import data_access.FakeUserDataAccessObject;
import data_access.SessionAssignmentDataAccess;

import entity.Assignment;
import entity.Course;
import entity.Instructor;
import entity.Student;
import interface_adapter.Resubmit.ResubmitController;
import interface_adapter.Resubmit.ResubmitPresenter;
import interface_adapter.Resubmit.ResubmitViewModel;
import interface_adapter.Submit.SubmitController;
import interface_adapter.Submit.SubmitPresenter;
import interface_adapter.Submit.SubmitViewModel;
import interface_adapter.Assignments.AssignmentsViewModel;
import interface_adapter.Assignments.AssignmentsController;
import interface_adapter.Assignments.AssignmentsPresenter;
import interface_adapter.CreateAssignment.CreateAssignmentController;
import interface_adapter.CreateAssignment.CreateAssignmentPresenter;
import interface_adapter.CreateAssignment.CreateAssignmentViewModel;
import interface_adapter.EditAssignment.EditAssignmentController;
import interface_adapter.EditAssignment.EditAssignmentPresenter;
import interface_adapter.EditAssignment.EditAssignmentViewModel;
import interface_adapter.ViewManagerModel;

import usecase.Assignments.AssignmentsInputBoundary;
import usecase.Assignments.AssignmentsInteractor;
import usecase.Assignments.AssignmentsOutputBoundary;
import usecase.CreateAssignment.CreateAssignmentDataAccessInterface;
import usecase.CreateAssignment.CreateAssignmentInputBoundary;
import usecase.CreateAssignment.CreateAssignmentInteractor;
import usecase.CreateAssignment.CreateAssignmentOutputBoundary;
import usecase.EditAssignment.EditAssignmentDataAccessInterface;
import usecase.EditAssignment.EditAssignmentInputBoundary;
import usecase.EditAssignment.EditAssignmentInteractor;
import usecase.EditAssignment.EditAssignmentOutputBoundary;
import usecase.Resubmit.ResubmitInputBoundary;
import usecase.Resubmit.ResubmitInteractor;
import usecase.Resubmit.ResubmitOutputBoundary;
import usecase.Submit.SubmitInputBoundary;
import usecase.Submit.SubmitInteractor;
import usecase.Submit.SubmitOutputBoundary;

import view.AssignmentView;
import view.CreateAssignmentView;
import view.EditAssignmentView;
import view.ResubmitView;
import view.SubmitView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppBuilder {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final Session session = new Session();
    // TODO: UserFactory

    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private AssignmentView assignmentView;
    private AssignmentsViewModel assignmentViewModel;
    private CreateAssignmentView createAssignmentView;
    private CreateAssignmentViewModel createAssignmentViewModel;
    private EditAssignmentView editAssignmentView;
    private EditAssignmentViewModel editAssignmentViewModel;
    private SubmitView submitView;
    private ResubmitView resubmitView;
    private SubmitViewModel submitViewModel;
    private ResubmitViewModel resubmitViewModel;

    private final FakeUserDataAccessObject userDataAccessObject = new FakeUserDataAccessObject();
    private final SessionAssignmentDataAccess sessionAssignmentDataAccess = new SessionAssignmentDataAccess(session);

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
        initializeMockData();

        // Initialize ViewModels
        assignmentViewModel = new AssignmentsViewModel();
        createAssignmentViewModel = new CreateAssignmentViewModel();
        editAssignmentViewModel = new EditAssignmentViewModel();
        submitViewModel = new SubmitViewModel();
        resubmitViewModel = new ResubmitViewModel();
    }

    private void initializeMockData() {
        // Create mock course
        Course course = new Course("Software Design", "CSC207");

        // Create mock assignments
        List<Assignment> assignments = new ArrayList<>();

        Assignment assignment1 = new Assignment();
        assignment1.setName("Assignment 1: Clean Architecture");
        assignment1.setDescription("Implement a simple application using Clean Architecture");
        assignment1.setDueDate(LocalDateTime.now().plusDays(7));
        assignment1.setCreationDate(LocalDateTime.now().minusDays(14));
        assignments.add(assignment1);

        Assignment assignment2 = new Assignment();
        assignment2.setName("Assignment 2: Design Patterns");
        assignment2.setDescription("Apply design patterns to refactor code");
        assignment2.setDueDate(LocalDateTime.now().plusDays(14));
        assignment2.setCreationDate(LocalDateTime.now().minusDays(7));
        assignments.add(assignment2);

        Assignment assignment3 = new Assignment();
        assignment3.setName("Assignment 3: Testing");
        assignment3.setDescription("Write comprehensive unit tests");
        assignment3.setDueDate(LocalDateTime.now().minusDays(2)); // Past due
        assignment3.setCreationDate(LocalDateTime.now().minusDays(21));
        assignments.add(assignment3);

        course.setAssignments(assignments);

        session.setCourse(course);

        //Student student = new Student("John Doe", "password123");
        //session.setUser(student);

        Instructor instructor = new Instructor("Prof. Smith", "password456");
        session.setUser(instructor);
    }

    public AppBuilder addAssignmentView() {
        assignmentView = new AssignmentView(assignmentViewModel);
        assignmentView.setViewManagerModel(viewManagerModel);
        cardPanel.add(assignmentView, assignmentView.getViewName());
        return this;
    }

    public AppBuilder addCreateAssignmentView() {
        createAssignmentView = new CreateAssignmentView(createAssignmentViewModel);
        cardPanel.add(createAssignmentView, createAssignmentView.getViewName());
        return this;
    }

    public AppBuilder addEditAssignmentView() {
        editAssignmentView = new EditAssignmentView(editAssignmentViewModel);
        cardPanel.add(editAssignmentView, editAssignmentView.getViewName());
        return this;
    }

    public AppBuilder addSubmitView() {
        submitView = new SubmitView(submitViewModel);
        submitView.setViewManagerModel(viewManagerModel);
        cardPanel.add(submitView, submitView.getViewName());
        return this;
    }

    public AppBuilder addResubmitView() {
        resubmitView = new ResubmitView(resubmitViewModel);
        resubmitView.setViewManagerModel(viewManagerModel);
        cardPanel.add(resubmitView, resubmitView.getViewName());
        return this;
    }

    public AppBuilder addAssignmentsUseCase() {
        final AssignmentsOutputBoundary assignmentsPresenter = new AssignmentsPresenter(
                assignmentViewModel);
        final AssignmentsInputBoundary assignmentsInteractor = new AssignmentsInteractor(
                sessionAssignmentDataAccess, assignmentsPresenter);

        AssignmentsController assignmentsController = new AssignmentsController(assignmentsInteractor);
        assignmentView.setAssignmentsController(assignmentsController);
        return this;
    }

    public AppBuilder addCreateAssignmentUseCase() {
        final CreateAssignmentOutputBoundary createAssignmentPresenter = new CreateAssignmentPresenter(
                createAssignmentViewModel, assignmentViewModel, viewManagerModel);
        final CreateAssignmentInputBoundary createAssignmentInteractor = new CreateAssignmentInteractor(
                (CreateAssignmentDataAccessInterface) sessionAssignmentDataAccess, createAssignmentPresenter);

        CreateAssignmentController createAssignmentController = new CreateAssignmentController(
                createAssignmentInteractor);
        createAssignmentView.setCreateAssignmentController(createAssignmentController);
        return this;
    }

    public AppBuilder addEditAssignmentUseCase() {
        final EditAssignmentOutputBoundary editAssignmentOutputBoundary = new EditAssignmentPresenter(
                editAssignmentViewModel, assignmentViewModel, viewManagerModel);
        final EditAssignmentInputBoundary editAssignmentInteractor = new EditAssignmentInteractor(
                (EditAssignmentDataAccessInterface) sessionAssignmentDataAccess, editAssignmentOutputBoundary);

        EditAssignmentController editAssignmentController = new EditAssignmentController(
                editAssignmentInteractor, viewManagerModel, assignmentViewModel, editAssignmentViewModel);
        editAssignmentView.setEditAssignmentController(editAssignmentController);

        // Also set the controller in AssignmentView so it can trigger the edit
        if (assignmentView != null) {
            assignmentView.setEditAssignmentController(editAssignmentController);
        }

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
                viewManagerModel, resubmitViewModel, submitViewModel);
        final ResubmitInputBoundary resubmitInteractor = new ResubmitInteractor(resubmitOutputBoundary, session);
        ResubmitController resubmitController = new ResubmitController(resubmitInteractor);
        resubmitView.setResubmitController(resubmitController);
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Assignment Management System");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        // Set AssignmentView as default and load assignments
        viewManagerModel.setState(assignmentView.getViewName());
        viewManagerModel.firePropertyChange();

        // Load assignments after view is set
        if (session.getCourse() != null) {
            assignmentView.loadAssignments(session.getCourse().getCourseCode());
        }

        return application;
    }
}
