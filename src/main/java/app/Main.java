package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Build the complete application for demo
        try {
            AppBuilder appBuilder = new AppBuilder();
            JFrame application = appBuilder
                    .addLoginView()
                    .addSignupView()
                    .addLoggedInView()
                    .addSignupUsecase()
                    .addLoginUsecase()
                    .build();

            application.pack();
            application.setLocationRelativeTo(null);
            application.setVisible(true);

            System.out.println("=== Coursework Platform Demo ===");
            System.out.println("Demo Instructions:");
            System.out.println("1. Click 'Register' to test registration");
            System.out.println("2. Try registering as both Student and Instructor");
            System.out.println("3. Test validation (short passwords, duplicate usernames)");
            System.out.println("4. After registration, login with your new account");
            System.out.println("5. See the role-specific dashboard");
            System.out.println("================================");

        } catch (Exception e) {
            System.err.println("Failed to start application: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Application failed to start: " + e.getMessage(),
                    "Startup Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
