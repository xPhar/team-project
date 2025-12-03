package usecase.submission;

public interface SubmissionInputBoundary {
    /**
     * Execute function for the interactor.
     * @param data the input data.
     */
    void execute(SubmissionInputData data);
}
