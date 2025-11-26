package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        AppBuilder appBuilder = new AppBuilder();

        JFrame application = appBuilder
                .addLoginView()
                .addLoggedInView()

                .addSubmitView()
                .addResubmitView()

                .addClassAverageView()

                .addCreateAssignmentView()
                .addEditAssignmentView()
                .addAssignmentView()

                .addSubmissionListView()
                .addSubmissionView()

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

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
