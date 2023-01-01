package com.github.tkutcher.jgrade;

import com.github.tkutcher.jgrade.gradedtest.AllGraderTests;
import com.github.tkutcher.jgrade.gradescope.GradescopeJsonFormatterTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        GraderTest.class,
        JGradeCommandLineTest.class,
        AllGraderTests.class,
        GradescopeJsonFormatterTest.class,
        CLITesterExecutionResultTest.class,
        DeductiveGraderStrategyTest.class,
})
public class AllJGradeTests { }
