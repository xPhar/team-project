package interfaceadapter.ClassAverage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.LoggedIn.LoggedInViewModel;
import usecase.ClassAverage.ClassAverageOutputBoundary;
import usecase.ClassAverage.ClassAverageOutputData;

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
        final ClassAverageState state = classAverageViewModel.getState();

        state.setAssignmentNames(data.getAssignmentNames());
        state.setStudentCount(data.getStudentCount());
        state.setMean(data.getMean());
        state.setMedian(data.getMedian());
        state.setStdDev(data.getStdDev());
        state.setMyScore(data.getMyScore());
        final JPanel chartPanel = buildHistogramChart(data);
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
     *
     * @param data histogram bucket data
     * @return JFreeChart histogram panel
     */
    private JPanel buildHistogramChart(ClassAverageOutputData data) {

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (String bucketName : data.getHistogram().keySet()) {
            final int count = data.getHistogram().get(bucketName);
            dataset.addValue(count, "Students", bucketName);
        }

        final JFreeChart chart = ChartFactory.createBarChart(
                data.getAssignmentName() + " Grade Distribution",
                "Range",
                "Students",
                dataset
        );

        final org.jfree.chart.plot.CategoryPlot plot = chart.getCategoryPlot();
        final org.jfree.chart.axis.NumberAxis rangeAxis = (org.jfree.chart.axis.NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(org.jfree.chart.axis.NumberAxis.createIntegerTickUnits());

        return new ChartPanel(chart);
    }
}
