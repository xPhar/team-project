package usecase.class_average;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import entity.Submission;

public class ClassAverageInteractor implements ClassAverageInputBoundary {

    private final ClassAverageUserDataAccessInterface submissionDAO;
    private final ClassAverageOutputBoundary presenter;

    public ClassAverageInteractor(ClassAverageUserDataAccessInterface submissionDAO,
                                  ClassAverageOutputBoundary presenter) {
        this.submissionDAO = submissionDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(ClassAverageInputData inputData) {

        if (inputData.getBack()) {
            // The state of loggedIn view shouldn't have changed, so no need to update it
            presenter.backToLoggedInView();
        }

        else {
            final String assignmentName = inputData.getAssignmentName();

            final List<Submission> submissions = submissionDAO.getSubmissionsFor(assignmentName);

            if (assignmentName == null || "Assignment".equals(assignmentName)) {
                final List<String> assignmentNames = submissionDAO.getAllAssignmentNames();
                final ClassAverageOutputData outputData = new ClassAverageOutputData(
                        assignmentNames,
                        0, 0, 0, 0, 0,
                        new LinkedHashMap<>(), " "
                );
                presenter.prepareSuccessView(outputData);
                return;
            }

            final List<Double> grades = new ArrayList<>();
            for (Submission s : submissions) {
                if (s.getStatus() == Submission.Status.GRADED) {
                    grades.add(s.getGrade());
                }
            }

            if (grades.isEmpty()) {
                presenter.prepareFailView("Assignment not graded yet!");
                return;
            }

            final double mean = computeMean(grades);
            final double median = computeMedian(grades);
            final double stdDev = computeStdDev(grades, mean);
            final int studentCount = grades.size();

            final double myScore = submissionDAO.getMyScore(assignmentName, submissionDAO.getCurrentUsername());
            final Map<String, Integer> histogram = buildHistogram(grades);
            final List<String> assignmentNames = submissionDAO.getAllAssignmentNames();
            final ClassAverageOutputData outputData = new ClassAverageOutputData(
                    assignmentNames,
                    studentCount,
                    mean,
                    median,
                    stdDev,
                    myScore,
                    histogram,
                    assignmentName
            );

            presenter.prepareSuccessView(outputData);
        }
    }

    private double computeMean(List<Double> grades) {
        double sum = 0;
        for (double g : grades) {
            sum += g;
        }
        return sum / grades.size();
    }

    private double computeMedian(List<Double> grades) {
        final List<Double> copy = new ArrayList<>(grades);
        Collections.sort(copy);
        final int n = copy.size();
        if (n % 2 == 1) {
            return copy.get(n / 2);
        }
        return (copy.get(n / 2 - 1) + copy.get(n / 2)) / 2.0;
    }

    private double computeStdDev(List<Double> grades, double mean) {
        double sum = 0;
        for (double g : grades) {
            sum += Math.pow(g - mean, 2);
        }
        return Math.sqrt(sum / grades.size());
    }

    /**
     * Buckets for X-axis:
     * 0–10, 11–20, ..., 91–100.
     *
     * @param grades the list of grades to build the histogram for
     * @return the histogram data
     */
    private Map<String, Integer> buildHistogram(List<Double> grades) {

        final String[] buckets = {
            "0-10", "11-20", "21-30", "31-40", "41-50",
            "51-60", "61-70", "71-80", "81-90", "91-100",
        };

        final Map<String, Integer> map = new LinkedHashMap<>();
        for (String b : buckets) {
            map.put(b, 0);
        }

        for (double g : grades) {
            final int idx = Math.min(9, (int) Math.floor((g - 1) / 10));
            final String bucketName = buckets[idx];
            map.put(bucketName, map.get(bucketName) + 1);
        }

        return map;
    }
}
