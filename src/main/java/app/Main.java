package app;

import data_access.GradeAPIDataAccessObject;
import entity.Assignment;
import entity.AssignmentBuilder;
import entity.Course;
import entity.User;
import org.json.JSONObject;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        AppBuilder appBuilder = new AppBuilder();

        // Should add verification that the user is a student of the course (they aren't right now :D)

        // TODO: change the user (I already added a submission for this user!)
        User exampleUser = new User("exampleUser", "abc123", "Example", "User");
        Course exampleCourse = new Course("exampleCourse", "course-course-CSC207");
        AssignmentBuilder assignmentBuilder = new AssignmentBuilder();

        assignmentBuilder.name("assignmentName1")
                .creationDate(LocalDateTime.of(2025, 11, 21, 12, 50, 0))
                .description("this is a description i guess..")
                .dueDate(LocalDateTime.of(2025, 11, 21, 23, 59, 59))
                .gracePeriod(2.0)
                .supportedFileTypes(List.of(".txt"));

        Assignment assignment = assignmentBuilder.build();

        Session session = new Session();
        session.setUser(exampleUser);
        session.setCourse(exampleCourse);
        session.setAssignment(assignment);

        JFrame application = appBuilder
                .setSession(session)
                // TODO: Login,signup,loggedIn views
                .addSubmitView()
                .addResubmitView()
                // TODO: other Views
                // TODO: signUp,LogIn use cases
                .addSubmitUseCase()
                .addResubmitUseCase()
                // TODO: other usecases
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
        // Here's an example from the CA Lab:
        /*
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addLoginView()
                .addSignupView()
                .addLoggedInView()
                .addSignupUseCase()
                .addLoginUseCase()
                .addChangePasswordUseCase()
                .addLogoutUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
         */
    }
}
