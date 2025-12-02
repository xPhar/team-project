package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import interface_adapter.submission_list.SubmissionListController;
import interface_adapter.submission_list.SubmissionListState;
import interface_adapter.submission_list.SubmissionListViewModel;
import interface_adapter.submission_list.SubmissionTableModel;

/**
 * View to show a list of submissions for a given assignment.
 */
public class SubmissionListView extends JPanel implements PropertyChangeListener {
    private static final int FONT_SIZE = 20;
    private static final int BORDER_SIZE = 10;
    private static final int SUBMISSION_TABLE_FONT_SIZE = 14;
    private static final int SUBMISSION_TABLE_ROW_HEIGHT = 28;
    private static final int SUBMISSION_TABLE_PREFERRED_HEIGHT = 28;
    private static final int BACK_BUTTON_MAX_W = 80;
    private static final int BACK_BUTTON_MAX_H = 30;
    private static final int BOTTOM_PANEL_MAX_H = 50;

    private final String viewName = "submissionList";
    private SubmissionListController submissionListController;

    private final SubmissionListViewModel submissionListModel;
    private final JTable submissionTable = new JTable();
    private final JLabel title;

    public SubmissionListView(
            SubmissionListViewModel submissionListModel
    ) {
        this.submissionListModel = submissionListModel;
        this.submissionListModel.addPropertyChangeListener(this);

        title = new JLabel("");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Helvetica", Font.BOLD, FONT_SIZE));

        final JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        titlePanel.setBorder(
                BorderFactory.createEmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE)
        );
        titlePanel.add(title);

        submissionTable.setModel(new SubmissionTableModel(new String[0][]));
        submissionTable.setFont(new Font(
                submissionTable.getFont().getFontName(), Font.PLAIN, SUBMISSION_TABLE_FONT_SIZE
        ));
        submissionTable.setRowHeight(SUBMISSION_TABLE_ROW_HEIGHT);
        submissionTable.getColumnModel().getColumn(2)
                .setPreferredWidth(SUBMISSION_TABLE_PREFERRED_HEIGHT);

        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(
                BorderFactory.createEmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE)
        );
        final JButton backButton = new JButton("Back");
        backButton.setMaximumSize(new Dimension(BACK_BUTTON_MAX_W, BACK_BUTTON_MAX_H));
        bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, BOTTOM_PANEL_MAX_H));
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
                            final String submitter =
                                    submissionTable.getValueAt(submissionTable.getSelectedRow(), 0)
                                            .toString();
                            submissionListController
                                    .executeChooseSubmission(submitter, title.getText());
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
