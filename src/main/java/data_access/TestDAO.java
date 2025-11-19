package data_access;

import entity.Submission;
import entity.SubmissionBuilder;
import usecase.SubmissionList.SubmissionListDataAccessInterface;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestDAO implements
        SubmissionListDataAccessInterface
{

    @Override
    public Submission getSubmission(String assignmentName, String submitter) {
        SubmissionBuilder builder = new SubmissionBuilder();
        builder.submitter("Indy");
        builder.submissionTime(LocalDateTime.now());
        builder.grade(20f);
        builder.status(Submission.Status.GRADED);
        builder.feedback("Nice work!\nKeep going!");
        return builder.build();
    }

    @Override
    public List<Submission> getSubmissionList(String assignmentName) {
        List<Submission> list = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            SubmissionBuilder builder = new SubmissionBuilder();
            builder.submitter("Indy");
            builder.submissionTime(LocalDateTime.now().plusSeconds(i));
            builder.status(Submission.Status.ON_TIME);
            if (Math.random() < 0.8) {
                builder.status(Submission.Status.GRADED);
                builder.grade(Math.round(Math.random() * 1000) / 10.0);
            }
            list.add(builder.build());
        }

        return list;
    }
}
