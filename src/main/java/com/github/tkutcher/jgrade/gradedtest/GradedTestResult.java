package com.github.tkutcher.jgrade.gradedtest;

/**
 * A class for the data that models a graded test. Primarily based on the
 * data needed for Gradescope's Autograder JSON. When creating
 * {@link GradedTestResult}s and working with visibility, use the public
 * constants in {@link Visibility}.
 */
public class GradedTestResult {

    // GradedTest annotation defaults
    static final String DEFAULT_NAME = "Unnamed Test";
    static final String DEFAULT_NUMBER = "";
    static final double DEFAULT_POINTS = 1.0;
    // This duplicates the default value in the GradedTestResult annotation,
    // but there is no clean way to get its value for a static constant.
    static final Visibility DEFAULT_VISIBILITY = Visibility.VISIBLE;

    private String name;
    private String number;
    private double points;
    private Visibility visibility;

    private double score;
    private StringBuilder output;
    private boolean passed;

    /**
     * Create a new GradedTestResult, setting the initial score to 0.
     * @param name The name/description of the test.
     * @param number The identifier for the question number.
     * @param points The number of points the test is worth.
     * @param visibility The visibility setting of the test.
     * @throws IllegalArgumentException If the visibility is not valid.
     */
    public GradedTestResult(String name, String number, double points, Visibility visibility)
            throws IllegalArgumentException {
        this.name = name;
        this.number = number;
        this.points = points;
        this.visibility = visibility;
        this.score = 0;
        this.output = new StringBuilder();
        this.passed = true;
    }

    // <editor-fold "desc="accessors">

    /**
     * Add output to the test result.
     * @param s String to append to the output.
     */
    public void addOutput(String s) {
        this.output.append(s);
    }

    /**
     * Set the score for the test.
     * @param score The score to set.
     * @throws RuntimeException if the score exceeds the points for the test.
     */
    public void setScore(double score) {
        if (score > this.points) {
            throw new RuntimeException("Cannot set score above max number of points");
        }
        this.score = score;
    }

    /**
     * Set the number of points the test is worth.
     * @param points The number of points to set.
     */
    public void setPoints(double points) {
        this.points = points;
    }

    /**
     * Set whether or not this result passed.
     * @param passed The value to set.
     */
    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    /**
     * Get the name of the test.
     * @return The name of the test.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the number of the test.
     * @return The number of the test.
     */
    public String getNumber() {
        return number;
    }

    /**
     * Get the points the test is worth.
     * @return The number of points the test is worth.
     */
    public double getPoints() {
        return points;
    }

    /**
     * Get the visibility setting of the test.
     * @return The visibility setting of the test.
     */
    public Visibility getVisibility() {
        return visibility;
    }

    /**
     * Get the score of the test.
     * @return The score of the test.
     */
    public double getScore() {
        return score;
    }

    /**
     * Get the output of the test.
     * @return The output of the test.
     */
    public String getOutput() {
        return this.output.toString();
    }


    /**
     * Determine if the test for this result was considered to have passed
     * or not.
     * @return True if the test passed (student got full credit), false
     *         otherwise.
     */
    public boolean passed() {
        return this.passed;
    }

    // <editor-fold "desc="accessors">
}
