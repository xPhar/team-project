package interface_adapter.submission_list;

import javax.swing.table.AbstractTableModel;

/**
 * Used for displaying submission data in a table in the submission list view.
 */
public class SubmissionTableModel extends AbstractTableModel {
    private static final int TOTAL_COLUMN_NUM = 3;
    private String[][] submissions;
    private final String[] header = {"Submitter", "Submission Time", "Grade"};

    /**
     * Instantiate a SubmissionTableModel.
     * @param submissions the list of submission to be displayed
     */
    public SubmissionTableModel(String[][] submissions) {
        this.submissions = submissions;
    }

    /**
     * Set the list of submission to be displayed.
     * @param submissions the list of submission to be displayed
     */
    public void setSubmissions(String[][] submissions) {
        this.submissions = submissions;
        fireTableDataChanged();
    }

    /**
     * Returns the list of the submission to be displayed.
     * @return the list of the submission to be displayed
     */
    public String[][] getSubmissions() {
        return this.submissions;
    }

    @Override
    public int getRowCount() {
        return submissions.length;
    }

    @Override
    public int getColumnCount() {
        return TOTAL_COLUMN_NUM;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return submissions[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int index) {
        return this.header[index];
    }
}
