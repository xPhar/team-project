package data_access;

import entity.Submission;
import usecase.Grade.GradeDataAccessInterface;
import usecase.SubmissionList.SubmissionListDataAccessInterface;
import usecase.Submission.SubmissionDataAccessInterface;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestDAO implements
        SubmissionListDataAccessInterface,
        GradeDataAccessInterface,
        SubmissionDataAccessInterface
{

    @Override
    public Submission getSubmission(String assignmentName, String submitter) {
        Submission.SubmissionBuilder builder = Submission.getBuilder();
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
            Submission.SubmissionBuilder builder = Submission.getBuilder();
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

    @Override
    public void grade(String assignment, String submitter, double grade, String feedback) {
        
    }


    @Override
    public void saveFile(File saveFile) {

    }
}
