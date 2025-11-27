package interface_adapter.class_average;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import usecase.class_average.ClassAverageOutputBoundary;
import usecase.class_average.ClassAverageOutputData;

import javax.swing.*;

public class ClassAveragePresenter implements ClassAverageOutputBoundary {

    private final ClassAverageViewModel classAverageViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    public ClassAveragePresenter(ClassAverageViewModel classAverageViewModel,
                                 LoggedInViewModel loggedInViewModel,
                                 ViewManagerModel viewManagerModel) {
        this.classAverageViewModel = classAverageViewModel;
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void backToLoggedInView() {
        // clear everything from classAverageViewModel's state
        classAverageViewModel.setState(new ClassAverageState());

        // switch to the loggedIn view
        this.viewManagerModel.setState(loggedInViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareSuccessView(ClassAverageOutputData data) {
        ClassAverageState state = classAverageViewModel.getState();

        state.setAssignmentNames(data.getAssignmentNames());
        state.setStudentCount(data.getStudentCount());
        state.setMean(data.getMean());
        state.setMedian(data.getMedian());
        state.setStdDev(data.getStdDev());
        state.setMyScore(data.getMyScore());
        JPanel chartPanel = buildHistogramChart(data);
        state.setChartPanel(chartPanel);
        classAverageViewModel.setState(state);
        classAverageViewModel.firePropertyChange();
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
