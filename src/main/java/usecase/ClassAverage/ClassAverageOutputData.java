package usecase.ClassAverage;

import java.util.List;
import java.util.Map;

/**
 * Output Data for the Class Average Use Case.
 * This object carries the processed results from the Interactor to the Presenter.
 */
public class ClassAverageOutputData {

    private final List<String> assignmentNames;
    private final int studentCount;
    private final double mean;
    private final double median;
    private final double stdDev;
    private final double myScore;
    private final Map<String, Integer> histogram;
    private final String assignmentName;

    public ClassAverageOutputData(List<String> assignmentNames,
                                  int studentCount,
                                  double mean,
                                  double median,
                                  double stdDev,
                                  double myScore,
                                  Map<String, Integer> histogram,
                                  String assignmentName) {
        this.assignmentNames = assignmentNames;
        this.studentCount = studentCount;
        this.mean = mean;
        this.median = median;
        this.stdDev = stdDev;
        this.myScore = myScore;
        this.histogram = histogram;
        this.assignmentName = assignmentName;
    }

    public List<String> getAssignmentNames() {
        return assignmentNames;
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

    public Map<String, Integer> getHistogram() {
        return histogram;
    }

    public String getAssignmentName() {
        return assignmentName;
    }
}

