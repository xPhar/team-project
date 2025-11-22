package view;

import entity.Assignment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AssignmentView extends JPanel {
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JButton newAssignmentButton = new JButton("Create New Assignment");
    private final JTextField courseField;
    private final DateTimeFormatter fmt =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public AssignmentView(String courseName) {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel title = new JLabel("Assignments");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));

        courseField = new JTextField(courseName, 15);
        courseField.setEditable(false);
        courseField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JPanel courseRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
        courseRow.add(new JLabel("Course:"));
        courseRow.add(courseField);

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setOpaque(false);
        top.add(title);
        top.add(Box.createVerticalStrut(4));
        top.add(courseRow);

        add(top, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new Object[]{"Name", "Due Date", "Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(newAssignmentButton);
        add(bottom, BorderLayout.SOUTH);
    }

    public void addAssignment(Assignment assignment) {
        String name = (assignment.getName() != null && !assignment.getName().isEmpty())
                ? assignment.getName()
                : "(unnamed)";

        LocalDateTime due = assignment.getDueDate();
        String dueText = (due != null) ? due.format(fmt) : "(no due date)";

        String status = computeStatus(assignment);

        tableModel.addRow(new Object[]{name, dueText, status});
    }

    private String computeStatus(Assignment a) {
        LocalDateTime due = a.getDueDate();
        if (due == null) return "Open";
        LocalDateTime now = LocalDateTime.now();
        return due.isBefore(now) ? "Closed" : "Open";
    }

    public void addNewAssignmentListener(java.awt.event.ActionListener l) {
        newAssignmentButton.addActionListener(l);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Assignment View Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            AssignmentView view = new AssignmentView("CSC207");
            frame.setContentPane(view);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
