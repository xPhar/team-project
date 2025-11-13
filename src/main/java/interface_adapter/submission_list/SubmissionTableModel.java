package interface_adapter.submission_list;

import entity.Submission;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Used for displaying submission data in a table in the submission list view
 */
public class SubmissionTableModel extends AbstractTableModel {
    private List<Submission> submissions;
    private final String[] header = {"Submitter","Submission Time", "Grade"};

    /**
     * Instantiate a SubmissionTableModel
     * @param submissions the list of submission to be displayed
     */
    public SubmissionTableModel(List<Submission> submissions) {
        this.submissions = submissions;
    }

    /**
     * Set the list of submission to be displayed
     * @param submissions the list of submission to be displayed
     */
    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
        fireTableDataChanged();
    }

    /**
     * Returns the list of the submission to be displayed
     * @return the list of the submission to be displayed
     */
    public List<Submission> getSubmissions() { return this.submissions; }

    @Override
    public int getRowCount() {
        return submissions.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Submission submission = submissions.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return submission.getSubmitter();
            case 1:
                LocalDateTime date = submission.getSubmissionTime();
                return date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"));
            case 2:
                double grade = submission.getGrade();
                Submission.Status status = submission.getStatus();
                if (status == Submission.Status.GRADED) {
                    return Math.round(grade * 10.0) / 10.0;
                }
                else {
                    return "pending";
                }
            default:
                throw new RuntimeException("Invalid column index");
        }
    }

    @Override
    public String getColumnName(int index) {
        return this.header[index];
    }
}
