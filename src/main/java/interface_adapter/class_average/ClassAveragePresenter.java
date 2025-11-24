package interface_adapter.class_average;

import interface_adapter.ViewManagerModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import usecase.class_average.ClassAverageOutputBoundary;
import usecase.class_average.ClassAverageOutputData;

import javax.swing.*;

public class ClassAveragePresenter implements ClassAverageOutputBoundary {

    private final ClassAverageViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public ClassAveragePresenter(ClassAverageViewModel viewModel,
                                 ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(ClassAverageOutputData data) {

        ClassAverageState state = viewModel.getState();

        state.setAssignmentNames(data.getAssignmentNames());
        state.setStudentCount(data.getStudentCount());
        state.setMean(data.getMean());
        state.setMedian(data.getMedian());
        state.setStdDev(data.getStdDev());
        state.setMyScore(data.getMyScore());
        JPanel chartPanel = buildHistogramChart(data);
        state.setChartPanel(chartPanel);
        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage);
    }

    /**
     * Build JFreeChart histogram panel based on histogram bucket data.
     */
    private JPanel buildHistogramChart(ClassAverageOutputData data) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (String bucketName : data.getHistogram().keySet()) {
            int count = data.getHistogram().get(bucketName);
            dataset.addValue(count, "Students", bucketName);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                data.getAssignmentName() + " Grade Distribution",
                "Range",
                "Students",
                dataset
        );

        return new ChartPanel(chart);
    }
}
