package com.github.tkutcher.jgrade.gradescope;

import com.github.tkutcher.jgrade.Grader;
import com.github.tkutcher.jgrade.OutputFormatter;
import com.github.tkutcher.jgrade.gradedtest.GradedTestResult;
import com.github.tkutcher.jgrade.gradedtest.Visibility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * A concrete formatter for a {@link Grader} where the output it produces
 * is the JSON a Gradescope Autograder can work with.
 */
public class GradescopeJsonFormatter implements OutputFormatter {

    private static final String EXECUTION_TIME = "execution_time";
    private static final String STDOUT_VISIBILITY = "stdout_visibility";
    private static final String TESTS = "tests";
    private static final String SCORE = "score";
    private static final String MAX_SCORE = "max_score";
    private static final String NAME = "name";
    private static final String NUMBER = "number";
    private static final String OUTPUT = "output";
    private static final String VISIBILITY = "visibility";

    private JSONObject json;
    private int prettyPrint;
    private Visibility visibility;
    private Visibility stdoutVisibility;

    /**
     * Creates an instance of the formatter. By default the pretty-print
     * option is off (the integer is negative).
     */
    public GradescopeJsonFormatter() {
        this.json = new JSONObject();
        this.prettyPrint = -1;
    }

    private boolean hasVisibility() {
        return this.visibility != null;
    }

    private boolean hasStdoutVisibility() {
        return this.stdoutVisibility != null;
    }

    // <editor-fold "desc="accessors">

    /**
     * Sets the visibility for all of the test cases.
     *
     * @param visibility The top-level visibility to use for all test cases.
     * @throws GradescopeJsonException If visibility not valid.
     */
    public void setVisibility(Visibility visibility) throws GradescopeJsonException {
        this.visibility = visibility;
    }

    /**
     * Sets the visibility for standard out during the run.
     *
     * @param visibility The visibility to set for standard out.
     * @throws GradescopeJsonException If visibility is not valid.
     */
    public void setStdoutVisibility(Visibility visibility) throws GradescopeJsonException {
        this.stdoutVisibility = visibility;
    }

    /**
     * Sets the pretty-print for the JSON to output. The integer is how many
     * spaces to add for each indent level. A negative integer corresponds to
     * disabling pretty-print. If non-negative, simply calls
     * {@link JSONObject#toString(int)}
     *
     * @param prettyPrint The integer for how much to indent
     */
    public void setPrettyPrint(int prettyPrint) {
        this.prettyPrint = prettyPrint;
    }

    // </editor-fold>


    @Override
    public String format(Grader grader) {
        json = new JSONObject();
        this.assemble(grader, json);
        try {
            return this.prettyPrint >= 0 ? this.json.toString(this.prettyPrint) : this.json.toString();
        } catch (JSONException e) {
            throw new InternalError(e);
        }
    }

    private JSONObject assemble(GradedTestResult r) {
        try {
            return new JSONObject()
                    .put(NAME, r.getName())
                    .put(SCORE, r.getScore())
                    .put(MAX_SCORE, r.getPoints())
                    .put(NUMBER, r.getNumber())
                    .put(OUTPUT, r.getOutput())
                    .put(VISIBILITY, r.getVisibility().getText());
        } catch (JSONException e) {
            throw new InternalError(e);
        }
    }

    private JSONArray assemble(List<GradedTestResult> l) {
        JSONArray testResults = new JSONArray();
        for (GradedTestResult r : l) {
            testResults.put(assemble(r));
        }
        return testResults;
    }

    private void assemble(Grader grader, JSONObject json) throws GradescopeJsonException {
        try {
            validateGrader(grader);
            if (grader.hasScore()) {
                json.put(SCORE, grader.getScore());
            }
            if (grader.hasMaxScore()) {
                json.put(MAX_SCORE, grader.getMaxScore());
            }
            if (grader.hasExecutionTime()) {
                json.put(EXECUTION_TIME, grader.getExecutionTime());
            }
            if (grader.hasOutput()) {
                json.put(OUTPUT, grader.getOutput());
            }
            if (this.hasVisibility()) {
                json.put(VISIBILITY, this.visibility.getText());
            }
            if (this.hasStdoutVisibility()) {
                json.put(STDOUT_VISIBILITY, this.stdoutVisibility.getText());
            }
            if (grader.hasGradedTestResults()) {
                json.put(TESTS, this.assemble(grader.getGradedTestResults()));
            }
        } catch (JSONException e) {
            throw new InternalError(e);
        }
    }

    private void validateGrader(Grader grader) {
        if (!(grader.hasScore() || grader.hasGradedTestResults())) {
            throw new GradescopeJsonException("Gradescope Json must have either tests or score set");
        }
    }
}
