package view;

import entity.Submission;
import interface_adapter.submission_list.SubmissionTableModel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SubmissionListView extends JPanel {
    private final String viewName = "submission list";

    private final JTable submissionTable = new JTable();

    public SubmissionListView() {
        // TODO Change this to the name of the assignment
        final JLabel title = new JLabel("Submissions");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font(title.getFont().getFontName(), Font.PLAIN, 20));

        ArrayList<Submission> submissions = new ArrayList<>();
        LocalDateTime time = LocalDateTime.now();
        for (int i = 0; i < 100; i++) {
            Submission newSubmission = new Submission();
            newSubmission.setSubmitter("Indy");
            newSubmission.setSubmissionTime(time);
            if (Math.random() <= 0.8) {
                newSubmission.setGrade(Math.random()*100);
                newSubmission.setStatus(Submission.Status.GRADED);
            }
            submissions.add(newSubmission);
            time = time.plusSeconds(1);
        }
        SubmissionTableModel model = new SubmissionTableModel(submissions);

        submissionTable.setModel(model);
        submissionTable.setFont(new Font(submissionTable.getFont().getFontName(), Font.PLAIN, 14));
        submissionTable.setRowHeight(20);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(new JScrollPane(submissionTable));
    }
}
