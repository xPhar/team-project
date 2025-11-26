package view;

import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.logged_in.LoggedInController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoggedInView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "logged in";

    private final LoggedInViewModel loggedInViewModel;
    private LoggedInController loggedInController;

    private JLabel nameLabel = new JLabel();
    private JTable assignmentTable;
    private JButton bottomRightButton;
    private JButton logOutButton;

    public LoggedInView(LoggedInViewModel loggedInViewModel) {

        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel welcome = new JLabel("Welcome to the Coursework Platform!");
        welcome.setFont(new Font("Helvetica", Font.BOLD, 18));

        nameLabel.setFont(new Font("Helvetica", Font.BOLD, 14));

        topPanel.add(welcome);
        topPanel.add(Box.createVerticalStrut(8));
        topPanel.add(nameLabel);

        add(topPanel, BorderLayout.NORTH);

        String[] colNames = {"Assignment Name", "Due Date", "Score", "Submitted"};
        Object[][] empty = {};

        DefaultTableModel model = new DefaultTableModel(empty, colNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        assignmentTable = new JTable(model);
        assignmentTable.setFillsViewportHeight(true);
        assignmentTable.setRowHeight(28);

        assignmentTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = assignmentTable.rowAtPoint(e.getPoint());
                int col = assignmentTable.columnAtPoint(e.getPoint());

                if (col == 0) {
                    String assignmentName =
                            assignmentTable.getValueAt(row, col).toString();
                    loggedInController.execute(false, assignmentName);
                }
            }
        });

        add(new JScrollPane(assignmentTable), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        logOutButton = new JButton("Log Out");
        logOutButton.addActionListener(this);

        bottomRightButton = new JButton();
        bottomRightButton.addActionListener(this);

        bottomPanel.add(logOutButton, BorderLayout.WEST);
        bottomPanel.add(bottomRightButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == logOutButton) {
            loggedInController.execute(true, null);
        }

        if (evt.getSource() == bottomRightButton) {
            LoggedInState state = loggedInViewModel.getState();

            if ("instructor".equals(state.getUserType())) {
                // TODO: Implement createAssignment
                // loggedInController.createAssignment();
            } else {
                loggedInController.execute(false, null);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            LoggedInState state = (LoggedInState) evt.getNewValue();

            nameLabel.setText(state.getUsername());

            if ("instructor".equals(state.getUserType())) {
                bottomRightButton.setText("Create Assignment");
            } else {
                bottomRightButton.setText("Show Distribution");
            }

            if (state.getAssignments() != null) {
                assignmentTable.setModel(
                        new DefaultTableModel(
                                state.getAssignments(),
                                new String[]{"Assignment Name", "Due Date", "Score", "Submitted"}
                        ) {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        }
                );
            }
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setLoggedInController(LoggedInController loggedInController) {
        this.loggedInController = loggedInController;
    }

}
