package app;

import org.json.JSONObject;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        AppBuilder appBuilder = new AppBuilder();

        JFrame application = appBuilder
                // TODO: Login,signup,loggedIn views
                .addLoginView()
                .addLoggedInView()
                .addSubmitView()
                .addResubmitView()
                .addClassAverageView()
                // TODO: other Views
                // TODO: signUp,LogIn use cases
                .addLoginUseCase()
                .addLoggedInUseCase()
                .addSubmitUseCase()
                .addResubmitUseCase()
                .addClassAverageUseCase()
                // TODO: other usecases
                .addSubmissionListView()
                .addSubmissionView()
                .addSubmissionListUseCase()
                .addSubmissionUseCase()
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
