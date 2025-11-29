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
        title.setFont(new Font(title.getFont().getFontName(), Font.PLAIN, 20));

        final JButton backButton = new JButton("Back");
        final JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.05;
        titlePanel.add(backButton, c);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.95;
        c.anchor = GridBagConstraints.CENTER;
        titlePanel.add(title, c);

        submissionTable.setModel(new SubmissionTableModel(new String[0][]));
        submissionTable.setFont(new Font(submissionTable.getFont().getFontName(), Font.PLAIN, 14));
        submissionTable.setRowHeight(20);
        submissionTable.getColumnModel().getColumn(2).setPreferredWidth(20);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(titlePanel);
        this.add(new JScrollPane(submissionTable));

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
