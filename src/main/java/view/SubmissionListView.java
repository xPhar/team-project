package view;

import entity.Submission;
import interface_adapter.submission_list.SubmissionListController;
import interface_adapter.submission_list.SubmissionListState;
import interface_adapter.submission_list.SubmissionListViewModel;
import interface_adapter.submission_list.SubmissionTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * View to show a list of submissions for a given assignment.
 */
public class SubmissionListView extends JPanel implements PropertyChangeListener {
    private final String viewName = "submissionList";
    private SubmissionListController submissionListController;

    private final SubmissionListViewModel submissionListModel;
    private final JTable submissionTable = new JTable();
    final JLabel title;

    public SubmissionListView(
            SubmissionListViewModel submissionListModel
    ) {
        this.submissionListModel = submissionListModel;
        this.submissionListModel.addPropertyChangeListener(this);

        title = new JLabel("");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Helvetica", Font.BOLD, 20));


        final JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titlePanel.add(title);

        submissionTable.setModel(new SubmissionTableModel(new String[0][]));
        submissionTable.setFont(new Font(submissionTable.getFont().getFontName(), Font.PLAIN, 14));
        submissionTable.setRowHeight(28);
        submissionTable.getColumnModel().getColumn(2).setPreferredWidth(20);

        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        final JButton backButton = new JButton("Back");
        backButton.setMaximumSize(new Dimension(80, 30));
        bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        bottomPanel.add(backButton, BorderLayout.WEST);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(titlePanel);
        this.add(new JScrollPane(submissionTable));
        this.add(bottomPanel);

        backButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        submissionListController.executeBack();
                    }
                }
        );
        submissionTable.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2 && submissionTable.getSelectedRow() != -1) {
                            String submitter = submissionTable.getValueAt(submissionTable.getSelectedRow(), 0).toString();
                            submissionListController.executeChooseSubmission(submitter, title.getText());
                        }
                    }
                }
        );
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SubmissionListState state = (SubmissionListState) evt.getNewValue();

        if (evt.getPropertyName().equals("state")) {
            title.setText(state.getTitle());
            submissionTable.setModel(state.getTableModel());
            state.getTableModel().fireTableDataChanged();
        }
    }

    public String getViewName() {
        return viewName;
    }

    public SubmissionListController getSubmissionListController() {
        return submissionListController;
    }

    public void setSubmissionListController(SubmissionListController submissionListController) {
        this.submissionListController = submissionListController;
    }
}
