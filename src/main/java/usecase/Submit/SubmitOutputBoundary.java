package usecase.Submit;

public interface SubmitOutputBoundary {

    void prepareSuccessView(SubmitOutputData submitOutputData);

    void prepareFailureView(SubmitOutputData submitOutputData);
}
