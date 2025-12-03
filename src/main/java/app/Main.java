package app;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();

        final JFrame application = appBuilder
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

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
