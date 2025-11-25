package app;

import javax.swing.*;

public class Main {
        public static void main(String[] args) {

                AppBuilder appBuilder = new AppBuilder();

                JFrame application = appBuilder
                                // TODO: Login,signup,loggedIn views
                                .addLoginView()
                                .addSubmitView()
                                .addResubmitView()
                                // TODO: other Views
                                .addCreateAssignmentView()
                                .addEditAssignmentView()
                                .addAssignmentView()
                                // TODO: signUp,LogIn use cases
                                .addLoginUseCase()
                                .addSubmitUseCase()
                                .addResubmitUseCase()
                                .addCreateAssignmentUseCase()
                                .addEditAssignmentUseCase()
                                .addAssignmentsUseCase()
                                // TODO: other usecases
                                .addSubmissionListView()
                                .addSubmissionView()
                                .addSubmissionListUseCase()
                                .addSubmissionUseCase()
                                .build();

                application.pack();
                application.setLocationRelativeTo(null);
                application.setVisible(true);
        }
}
