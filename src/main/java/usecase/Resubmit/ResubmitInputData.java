package usecase.Resubmit;

import java.time.LocalDateTime;

public class ResubmitInputData {
    private final LocalDateTime time;

    public ResubmitInputData(LocalDateTime time){
        this.time = time;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
