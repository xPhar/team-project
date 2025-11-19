package interface_adapter.logged_in;

public class LoggedInController {

    public LoggedInController() {}

    // Fake method so LoggedInView can call something
    public void execute() {
        System.out.println("[FakeLoggedInController] execute() called");
    }
}
