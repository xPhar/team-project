package usecase.submit;

public interface SubmitOutputBoundary {

    void prepareSuccessView(SubmitOutputData submitOutputData);

    void prepareFailureView(SubmitOutputData submitOutputData);

    void switchToLoggedInView();
}
