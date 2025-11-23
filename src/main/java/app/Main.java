package app;

import javax.swing.*;

public class Main {
        public static void main(String[] args) {

                AppBuilder appBuilder = new AppBuilder();

                JFrame application = appBuilder
                                .addAssignmentView()
                                .addCreateAssignmentView()
                                .addEditAssignmentView()
                                .addSubmitView()
                                .addResubmitView()
                                // TODO: other Views
                                .addAssignmentsUseCase()
                                .addCreateAssignmentUseCase()
                                .addEditAssignmentUseCase()
                                .addSubmitUseCase()
                                .addResubmitUseCase()
                                // TODO: other usecases
                                .build();

                application.pack();
                application.setLocationRelativeTo(null);
                application.setVisible(true);
        }
}
