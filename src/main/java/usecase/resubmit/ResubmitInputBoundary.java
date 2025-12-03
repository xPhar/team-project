package usecase.resubmit;

public interface ResubmitInputBoundary {

    void execute(ResubmitInputData inputData);

    void goBack();
}
