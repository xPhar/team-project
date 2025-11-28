package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();

        JFrame application = appBuilder
                .addLoginView()
                .addSignupView()
                .addLoggedInView()

                .addSubmitView()
                .addResubmitView()

                .addClassAverageView()

                .addCreateAssignmentView()
                .addEditAssignmentView()
                .addAssignmentView()

                .addSubmissionListView()
                .addSubmissionView()

                .addSignupUseCase()
                .addLoginUseCase()
                .addLoggedInUseCase()

                .addSubmitUseCase()
                .addResubmitUseCase()

                .addClassAverageUseCase()

                .addCreateAssignmentUseCase()
                .addEditAssignmentUseCase()
                .addAssignmentsUseCase()

                .addSubmissionListUseCase()
                .addSubmissionUseCase()

                .build();

        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
