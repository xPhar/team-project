package interfaceadapter.ClassAverage;

import java.util.List;

import javax.swing.JPanel;

/**
 * The State object for the ClassAverage ViewModel.
 * This stores all UI-related data that the View will display.
 */
public class ClassAverageState {

    private List<String> assignmentNames;
    private int studentCount;
    private double mean;
    private double median;
    private double stdDev;
    private double myScore;
    private JPanel chartPanel;

    public ClassAverageState() {
        this.assignmentNames = null;
        this.chartPanel = null;
        this.studentCount = 0;
        this.mean = 0;
        this.median = 0;
        this.stdDev = 0;
        this.myScore = 0;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public double getMean() {
        return mean;
    }

    public double getMedian() {
        return median;
    }

    public double getStdDev() {
        return stdDev;
    }

    public double getMyScore() {
        return myScore;
    }

    public JPanel getChartPanel() {
        return chartPanel;
    }

    public List<String> getAssignmentNames() {
        return assignmentNames;
    }

    public void setAssignmentNames(List<String> assignmentNames) {
        this.assignmentNames = assignmentNames;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public void setMedian(double median) {
        this.median = median;
    }

    public void setStdDev(double stdDev) {
        this.stdDev = stdDev;
    }

    public void setMyScore(double myScore) {
        this.myScore = myScore;
    }

    public void setChartPanel(JPanel chartPanel) {
        this.chartPanel = chartPanel;
    }
}
