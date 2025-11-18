package view;

import entity.Submission;
import entity.SubmissionBuilder;
import interface_adapter.submission_list.SubmissionListState;
import interface_adapter.submission_list.SubmissionListViewModel;
import interface_adapter.submission_list.SubmissionTableModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SubmissionListView extends JPanel implements PropertyChangeListener {
    private final String viewName = "SubmissionList";

    private final SubmissionListViewModel submissionListModel;
    private final JTable submissionTable = new JTable();
    final JLabel title;

    public SubmissionListView(SubmissionListViewModel submissionListModel) {
        this.submissionListModel = submissionListModel;
        this.submissionListModel.addPropertyChangeListener(this);

        title = new JLabel("");
//        title.setAlignmentX(Component.CENTER_ALIGNMENT);
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

        submissionTable.setModel(new SubmissionTableModel(new ArrayList<Submission>()));
        submissionTable.setFont(new Font(submissionTable.getFont().getFontName(), Font.PLAIN, 14));
        submissionTable.setRowHeight(20);
        submissionTable.getColumnModel().getColumn(2).setPreferredWidth(20);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(titlePanel);
        this.add(new JScrollPane(submissionTable));

        populateTestData();
    }

    private void populateTestData() {
        SubmissionListState state = submissionListModel.getState();
        List<Submission> list = new ArrayList<Submission>();
        SubmissionTableModel tableModel = new SubmissionTableModel(list);
        for (int i = 0; i < 50; i++) {
            SubmissionBuilder submissionBuilder = new SubmissionBuilder();
            submissionBuilder.submitter("Indy").submissionTime(LocalDateTime.now());
            if (Math.random() > 0.2) {
                submissionBuilder.grade(Math.round(Math.random() * 1000) / 10.0)
                        .status(Submission.Status.GRADED);
            }
            list.add(submissionBuilder.build());
        }
        state.setTableModel(tableModel);
        state.setTitle("Some Assignment");
        submissionListModel.firePropertyChange("title");
        submissionListModel.firePropertyChange("tableModel");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SubmissionListState state = (SubmissionListState) evt.getNewValue();

        if (evt.getPropertyName().equals("title")) {
            title.setText(state.getTitle());
        }
        else if (evt.getPropertyName().equals("tableModel")) {
            submissionTable.setModel(state.getTableModel());
            state.getTableModel().fireTableDataChanged();
        }
    }

    public String getViewName() {
        return viewName;
    }
}
