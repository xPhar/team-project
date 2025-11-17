package app;

import data_access.FileToStringDataAccessObject;
import data_access.GradeAPIDataAccessObject;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Main {
    public static void main(String[] args) {
        // TODO: Build app
//        AppBuilder appBuilder = new AppBuilder();
//        JFrame application = appBuilder
//                .addSubmitView()
//                .build();
//        application.pack();
//        application.setLocationRelativeTo(null);
//        application.setVisible(true);

        GradeAPIDataAccessObject gradeAPIDataAccessObject = new GradeAPIDataAccessObject();

        String username = "student";
        String password = "password";

        System.out.println(gradeAPIDataAccessObject.modifyUser(username, password));


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
