package data_access;

import entity.User;
import usecase.login.LoginDataAccessInterface;
import usecase.signup.SignupDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

public class DummyDataAccessObject implements LoginDataAccessInterface,
        SignupDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();
    private String currentUsername = "";

    @Override
    public boolean existsByName(String username) {
        return users.containsKey(username);
    }

    @Override
    public void save(User user) {
        if (user != null && user.getName() != null) {
            users.put(user.getName(), user);
            System.out.println("User saved: " + user.getName() + " (Total users: " + users.size() + ")");
        }
    }

    @Override
    public User get(String username) {
        return users.get(username);
    }

    @Override
    public void setCurrentUsername(String name) {
        this.currentUsername = name;
        System.out.println("Current user set to: " + name);
    }

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    // Helper methods for testing and debugging
    public int getUserCount() {
        return users.size();
    }

    public void clearAllUsers() {
        users.clear();
        currentUsername = "";
        System.out.println("All users cleared.");
    }

    public boolean isUserLoggedIn() {
        return !currentUsername.isEmpty() && users.containsKey(currentUsername);
    }

    public User getCurrentUser() {
        return users.get(currentUsername);
    }

    public void printAllUsers() {
        System.out.println("=== Registered Users ===");
        if (users.isEmpty()) {
            System.out.println("No users registered.");
        } else {
            for (String username : users.keySet()) {
                User user = users.get(username);
                System.out.println("Username: " + username +
                        ", Type: " + user.getClass().getSimpleName() +
                        ", Password: " + user.getPassword());
            }
        }
        System.out.println("Current User: " + (currentUsername.isEmpty() ? "None" : currentUsername));
        System.out.println("========================");
    }
}