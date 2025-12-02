package interface_adapter.submission_list;

/**
 * The state for the submission list view.
 */
public class SubmissionListState {
    private SubmissionTableModel tableModel;
    private String title;

    public SubmissionTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(SubmissionTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
