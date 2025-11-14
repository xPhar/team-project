package view;

import interface_adapter.class_average.ClassAverageController;
import interface_adapter.class_average.ClassAverageState;
import interface_adapter.class_average.ClassAverageViewModel;
import interface_adapter.class_average.LoggedInController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * View for showing class average and grade distribution of an assignment.
 */
public class ClassAverageView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "class average";

    private final ClassAverageViewModel classAverageViewModel;

    private ClassAverageController classAverageController;
    private LoggedInController loggedInController;

    private final JComboBox<String> assignmentComboBox;

    private final JLabel studentsLabel;
    private final JLabel meanLabel;
    private final JLabel medianLabel;
    private final JLabel stdDevLabel;

    private final JPanel chartPanel;

    private final JLabel myScoreLabel;
    private final JButton backButton;

    public ClassAverageView(ClassAverageViewModel viewModel) {
        this.classAverageViewModel = viewModel;
        this.classAverageViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel topRow = new JPanel();
        topRow.setLayout(new FlowLayout(FlowLayout.LEFT));

        assignmentComboBox = new JComboBox<>();
        assignmentComboBox.addItem("Assignment");

        assignmentComboBox.addActionListener(e -> {
            if (classAverageController != null) {
                String selected = (String) assignmentComboBox.getSelectedItem();
                if (selected != null && !selected.equals("Assignment")) {
                    classAverageController.execute(selected);
                }
            }
        });

        topRow.add(assignmentComboBox);

        JPanel statsRow = new JPanel();
        statsRow.setLayout(new FlowLayout(FlowLayout.LEFT));

        studentsLabel = new JLabel("Students: ");
        meanLabel = new JLabel("   Mean: ");
        medianLabel = new JLabel("   Median: ");
        stdDevLabel = new JLabel("   Std. Dev.: ");

        statsRow.add(studentsLabel);
        statsRow.add(meanLabel);
        statsRow.add(medianLabel);
        statsRow.add(stdDevLabel);

        chartPanel = new JPanel();
        chartPanel.setLayout(new BorderLayout());
        chartPanel.setPreferredSize(new Dimension(600, 300));

        JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new BorderLayout());

        myScoreLabel = new JLabel("My Score: ");
        bottomRow.add(myScoreLabel, BorderLayout.WEST);

        backButton = new JButton("Back");
        bottomRow.add(backButton, BorderLayout.EAST);

        backButton.addActionListener(this);

        this.add(topRow);
        this.add(statsRow);
        this.add(chartPanel);
        this.add(bottomRow);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(backButton) && loggedInController != null) {
            loggedInController.execute();
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (!"state".equals(evt.getPropertyName())) return;

        ClassAverageState state = (ClassAverageState) evt.getNewValue();

        if (state.getAssignmentNames() != null) {
            assignmentComboBox.removeAllItems();
            assignmentComboBox.addItem("Assignment");
            for (String name : state.getAssignmentNames()) {
                assignmentComboBox.addItem(name);
            }
        }

        studentsLabel.setText("Students: " + state.getStudentCount());
        meanLabel.setText("   Mean: " + state.getMean());
        medianLabel.setText("   Median: " + state.getMedian());
        stdDevLabel.setText("   Std. Dev.: " + state.getStdDev());

        myScoreLabel.setText("My Score: " + state.getMyScore());

        if (state.getChartPanel() != null) {
            chartPanel.removeAll();
            chartPanel.add(state.getChartPanel(), BorderLayout.CENTER);
            chartPanel.revalidate();
            chartPanel.repaint();
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setClassAverageController(ClassAverageController classAverageController) {
        this.classAverageController = classAverageController;
    }

    public void setLoggedInController(LoggedInController loggedInController) {
        this.loggedInController = loggedInController;
    }
}
