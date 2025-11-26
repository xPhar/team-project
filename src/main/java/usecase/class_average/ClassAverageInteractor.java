package usecase.class_average;

import entity.Submission;
import usecase.login.LoginOutputData;

import java.util.*;

public class ClassAverageInteractor implements ClassAverageInputBoundary {

    private final ClassAverageUserDataAccessInterface submissionDAO;
    private final ClassAverageOutputBoundary presenter;
    private final String currentUsername;

    public ClassAverageInteractor(ClassAverageUserDataAccessInterface submissionDAO,
                                  ClassAverageOutputBoundary presenter,
                                  String currentUsername) {
        this.submissionDAO = submissionDAO;
        this.presenter = presenter;
        this.currentUsername = currentUsername;
    }

    @Override
    public void execute(ClassAverageInputData inputData) {

        if (inputData.getBack()) {
            // The state of loggedIn view shouldn't have changed, so no need to update it
            presenter.backToLoggedInView();
        }

        String assignmentName = inputData.getAssignmentName();

        List<Submission> submissions = submissionDAO.getSubmissionsFor(assignmentName);

        if (assignmentName == null || assignmentName.equals("Assignment")) {
            List<String> assignmentNames = submissionDAO.getAllAssignmentNames();
            ClassAverageOutputData outputData = new ClassAverageOutputData(
                    assignmentNames,
                    0, 0, 0, 0, 0,
                    new LinkedHashMap<>(), " "
            );
            presenter.prepareSuccessView(outputData);
            return;
        }

        List<Double> grades = new ArrayList<>();
        for (Submission s : submissions) {
            if (s.getStatus() == Submission.Status.GRADED) {
                grades.add(s.getGrade());
            }
        }

        if (grades.isEmpty()) {
            presenter.prepareFailView("Assignment not graded yet!");
            return;
        }

        double mean = computeMean(grades);
        double median = computeMedian(grades);
        double stdDev = computeStdDev(grades, mean);
        int studentCount = grades.size();

        double myScore = submissionDAO.getMyScore(assignmentName, currentUsername);
        Map<String, Integer> histogram = buildHistogram(grades);
        List<String> assignmentNames = submissionDAO.getAllAssignmentNames();
        ClassAverageOutputData outputData = new ClassAverageOutputData(
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

    private double computeMean(List<Double> grades) {
        double sum = 0;
        for (double g : grades) sum += g;
        return sum / grades.size();
    }

    private double computeMedian(List<Double> grades) {
        List<Double> copy = new ArrayList<>(grades);
        Collections.sort(copy);
        int n = copy.size();
        if (n % 2 == 1) return copy.get(n / 2);
        return (copy.get(n / 2 - 1) + copy.get(n / 2)) / 2.0;
    }

    private double computeStdDev(List<Double> grades, double mean) {
        double sum = 0;
        for (double g : grades) sum += Math.pow(g - mean, 2);
        return Math.sqrt(sum / grades.size());
    }

    /**
     * Buckets for X-axis:
     * 0–10, 11–20, ..., 91–100
     */
    private Map<String, Integer> buildHistogram(List<Double> grades) {

        String[] buckets = {
                "0-10", "11-20", "21-30", "31-40", "41-50",
                "51-60", "61-70", "71-80", "81-90", "91-100"
        };

        Map<String, Integer> map = new LinkedHashMap<>();
        for (String b : buckets) map.put(b, 0);

        for (double g : grades) {
            int idx;
            if (g <= 10) idx = 0;
            else if (g <= 20) idx = 1;
            else if (g <= 30) idx = 2;
            else if (g <= 40) idx = 3;
            else if (g <= 50) idx = 4;
            else if (g <= 60) idx = 5;
            else if (g <= 70) idx = 6;
            else if (g <= 80) idx = 7;
            else if (g <= 90) idx = 8;
            else idx = 9;

            String bucketName = buckets[idx];
            map.put(bucketName, map.get(bucketName) + 1);
        }

        return map;
    }
}
