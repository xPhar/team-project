package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoggedInViewPreview extends JPanel {

    public LoggedInViewPreview() {

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel welcome = new JLabel("Welcome to the Coursework Platform!");
        welcome.setFont(new Font("Helvetica", Font.BOLD, 18));

        JLabel nameLabel = new JLabel("Name: First Last");
        nameLabel.setFont(new Font("Helvetica", Font.BOLD, 14));

        topPanel.add(welcome);
        topPanel.add(Box.createVerticalStrut(8));
        topPanel.add(nameLabel);

        add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"Assignment Name", "Due Date", "Score", "Submitted"};

        Object[][] data = {
                {"A1", "2025-10-01", "90", "Yes"},
                {"A2", "2025-10-12", "-", "No"},
                {"A3", "2025-11-01", "87", "Yes"},
                {"Project", "2025-12-01", "-", "No"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // completely non-editable
            }
        };

        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(28);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                if (col == 0) {
                    String assignmentName = table.getValueAt(row, col).toString();
                    System.out.println("Clicked assignment: " + assignmentName);
                    JOptionPane.showMessageDialog(
                            null,
                            "Opening page for: " + assignmentName
                    );
                }
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton logoutBtn = new JButton("Log Out");
        JButton rightButton = new JButton("Show Distribution"); // or "Create Assignment"

        bottomPanel.add(logoutBtn, BorderLayout.WEST);
        bottomPanel.add(rightButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Logged In View Preview");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);

        frame.setContentPane(new LoggedInViewPreview());
        frame.setVisible(true);
    }
}
