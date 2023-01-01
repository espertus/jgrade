package com.github.tkutcher.jgrade.gradedtest;

import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SelectClasses;

@Suite
@SelectClasses({
        GradedTestListenerTest.class,
        GradedTestResultTest.class})
public class AllGraderTests {
}
