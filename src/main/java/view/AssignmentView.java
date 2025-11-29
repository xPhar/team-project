package view;

import interface_adapter.Assignments.AssignmentDTO;
import interface_adapter.Assignments.AssignmentsState;
import interface_adapter.Assignments.AssignmentsViewModel;
import interface_adapter.Assignments.AssignmentsController;
import interface_adapter.EditAssignment.EditAssignmentController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AssignmentView extends JPanel implements PropertyChangeListener {
    private final String viewName = "Assignments";
    private final AssignmentsViewModel assignmentViewModel;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JButton newAssignmentButton;
    private final JLabel courseLabel;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
    private final JLabel errorLabel = new JLabel("");

    private AssignmentsController assignmentsController;
    private EditAssignmentController editAssignmentController;
    private List<AssignmentDTO> currentAssignments;
    private boolean isInstructor;
    private String courseCode;

    // Colors
    private final Color PRIMARY_COLOR = new Color(79, 70, 229); // Indigo-600
    private final Color BACKGROUND_COLOR = new Color(249, 250, 251); // Gray-50
    private final Color SURFACE_COLOR = Color.WHITE;
    private final Color TEXT_PRIMARY = new Color(17, 24, 39); // Gray-900
    private final Color TEXT_SECONDARY = new Color(107, 114, 128); // Gray-500
    private final Color BORDER_COLOR = new Color(229, 231, 235); // Gray-200

    // Fonts
    private final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private final Font SUBHEADER_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);

    public AssignmentView(AssignmentsViewModel assignmentViewModel) {
        this.assignmentViewModel = assignmentViewModel;
        this.assignmentViewModel.addPropertyChangeListener(this);

        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(30, 40, 30, 40));

        // Add listener to refresh assignments when view becomes visible
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                loadAssignments();
            }
        });

        // Header Section
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 5));
        titlePanel.setOpaque(false);

        JLabel title = new JLabel("Assignments");
        title.setFont(HEADER_FONT);
        title.setForeground(TEXT_PRIMARY);

        courseLabel = new JLabel("Course: Loading...");
        courseLabel.setFont(SUBHEADER_FONT);
        courseLabel.setForeground(TEXT_SECONDARY);

        titlePanel.add(title);
        titlePanel.add(courseLabel);

        headerPanel.add(titlePanel, BorderLayout.WEST);

        errorLabel.setForeground(new Color(220, 38, 38)); // Red-600
        errorLabel.setFont(BODY_FONT);
        headerPanel.add(errorLabel, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);

        // Table Section
        tableModel = new DefaultTableModel(
                new Object[] { "Name", "Due Date", "Status", "Action" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Only action column is editable
            }
        };

        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(45);
        table.setFont(BODY_FONT);
        table.setShowVerticalLines(false);
        table.setGridColor(BORDER_COLOR);
        table.setSelectionBackground(new Color(243, 244, 246)); // Gray-100
        table.setSelectionForeground(TEXT_PRIMARY);

        // Table Header Styling
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(SURFACE_COLOR);
        table.getTableHeader().setForeground(TEXT_SECONDARY);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

        // Column Styling
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn("Action").setMaxWidth(120);
        table.getColumn("Action").setMinWidth(120);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        scroll.getViewport().setBackground(SURFACE_COLOR);
        add(scroll, BorderLayout.CENTER);

        // Footer Section
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setOpaque(false);

        newAssignmentButton = new JButton("Create Assignment");
        stylePrimaryButton(newAssignmentButton);

        bottom.add(newAssignmentButton);
        add(bottom, BorderLayout.SOUTH);

        // Add listener for Create New Assignment button
        newAssignmentButton.addActionListener(e -> handleCreateAssignment());

        // Add listener for the table row to switch to submission list view
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                if (col != 3 && e.getClickCount() == 2) {
                    handleAssignmentDoubleClick(row);
                }
            }
        });
    }

    private void stylePrimaryButton(JButton btn) {
        btn.setFont(BUTTON_FONT);
        btn.setForeground(Color.WHITE);
        btn.setBackground(PRIMARY_COLOR);
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        AssignmentsState state = (AssignmentsState) evt.getNewValue();
        updateView(state);
    }

    private void updateView(AssignmentsState state) {
        courseLabel.setText("Course: " + state.getCourseName());
        this.currentAssignments = state.getAssignments();
        this.isInstructor = state.isInstructor();

        tableModel.setRowCount(0);
        tableModel.setColumnCount(4);
        tableModel.setColumnIdentifiers(new Object[] { "Name", "Due Date", "Status", "Action" });
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn("Action").setMaxWidth(120);
        table.getColumn("Action").setMinWidth(120);

        for (int i = 0; i < currentAssignments.size(); i++) {
            addAssignmentRow(currentAssignments.get(i), i);
        }

        newAssignmentButton.setVisible(state.isInstructor());

        if (state.getErrorMessage() != null && !state.getErrorMessage().isEmpty()) {
            errorLabel.setText(state.getErrorMessage());
        } else {
            errorLabel.setText("");
        }
    }

    private void addAssignmentRow(AssignmentDTO assignment, int index) {
        String name = (assignment.getName() != null && !assignment.getName().isEmpty())
                ? assignment.getName()
                : "(unnamed)";

        LocalDateTime due = assignment.getDueDate();
        String dueText = (due != null) ? due.format(fmt) : "(no due date)";

        String status = computeStatus(assignment);

        if (isInstructor) {
            tableModel.addRow(new Object[] { name, dueText, status, "Edit" });
        } else {
            String buttonText = getButtonText(assignment);
            tableModel.addRow(new Object[] { name, dueText, status, buttonText });
        }
    }

    private String computeStatus(AssignmentDTO assignment) {
        LocalDateTime due = assignment.getDueDate();
        LocalDateTime now = LocalDateTime.now();

        if (due == null) {
            return isInstructor ? "Open" : "Not Submitted";
        }

        if (isInstructor) {
            double gracePeriodHours = assignment.getGracePeriod();
            LocalDateTime graceDeadline = due.plusHours((long) gracePeriodHours);
            return now.isAfter(graceDeadline) ? "Closed" : "Open";
        }

        double gracePeriodHours = assignment.getGracePeriod();
        LocalDateTime graceDeadline = due.plusHours((long) gracePeriodHours);

        if (now.isAfter(graceDeadline)) {
            return "Closed";
        }

        if (now.isAfter(due)) {
            return "Overdue";
        }

        return "Open";
    }

    private String getButtonText(AssignmentDTO assignment) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime due = assignment.getDueDate();

        if (due == null) {
            return "Submit";
        }

        double gracePeriodHours = assignment.getGracePeriod();
        LocalDateTime graceDeadline = due.plusHours((long) gracePeriodHours);

        if (now.isAfter(graceDeadline)) {
            return "Closed";
        }

        return "Submit";
    }

    private void handleCreateAssignment() {
        if (assignmentsController != null) {
            assignmentsController.switchToCreateAssignmentView();
        }
    }

    private void handleAssignmentAction(int assignmentIndex) {
        if (assignmentIndex < 0 || assignmentIndex >= currentAssignments.size()) {
            return;
        }

        AssignmentDTO assignment = currentAssignments.get(assignmentIndex);

        if (isInstructor) {
            if (editAssignmentController != null) {
                editAssignmentController.prepareEdit(
                        courseCode,
                        assignment.getName(),
                        assignment.getDescription(),
                        assignment.getDueDate(),
                        assignment.getGracePeriod(),
                        assignment.getSupportedFileTypes());
            } else {
                JOptionPane.showMessageDialog(this,
                        "Edit controller not initialized.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            assignmentsController.switchToSubmitView();
        }
    }

    private void handleAssignmentDoubleClick(int assignmentIndex) {
        if (assignmentIndex < 0 || assignmentIndex >= currentAssignments.size()) {
            return;
        }

        AssignmentDTO assignment = currentAssignments.get(assignmentIndex);

        if (isInstructor) {
            assignmentsController.switchToSubmissionListView(assignment.getName());
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Segoe UI", Font.PLAIN, 12));
            setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
            setBackground(SURFACE_COLOR);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());

            String text = getText();
            if ("Closed".equals(text) && !isInstructor) {
                setEnabled(false);
                setForeground(TEXT_SECONDARY);
            } else {
                setEnabled(true);
                if ("Submit".equals(text))
                    setForeground(new Color(5, 150, 105)); // Green-600
                else if ("Resubmit".equals(text))
                    setForeground(new Color(217, 119, 6)); // Amber-600
                else if ("Edit".equals(text))
                    setForeground(PRIMARY_COLOR);
                else
                    setForeground(TEXT_PRIMARY);
            }

            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private int currentRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            button.addActionListener(e -> {
                fireEditingStopped();
                handleAssignmentAction(currentRow);
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            currentRow = row;

            if ("Closed".equals(label) && !isInstructor) {
                button.setEnabled(false);
            } else {
                button.setEnabled(true);
            }

            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }
    }

    public void setAssignmentsController(AssignmentsController controller) {
        this.assignmentsController = controller;
    }

    public void setEditAssignmentController(EditAssignmentController controller) {
        this.editAssignmentController = controller;
    }

    public String getViewName() {
        return viewName;
    }

    public void loadAssignments() {
        if (assignmentsController != null) {
            assignmentsController.execute();
        }
    }
}
