package staff.hello;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.github.tkutcher.jgrade.gradedtest.GradedTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import student.hello.Greeting;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Timeout(value = 10) // fail any test if not done in 10 seconds
public class HelloTest {

    // I use this so I can run locally and verify output
    static final boolean DEBUG = false;

    private static final String GREETING = "Hello";
    private Greeting unit;

    // Running with the DEBUG flag enabled tests the staff solution.
    @BeforeEach
    public void initUnit() {
        this.unit = DEBUG ? new Hello(GREETING) : new student.hello.Hello(GREETING);
    }

    @Test
    @GradedTest(name = "greet() works")
    public void defaultGreeting() {
        assertEquals(GREETING, unit.greet());
    }

    @Test
    @GradedTest(name = "greet(String who) works", points = 2.0)
    public void greetSomebody() {
        assertEquals(GREETING + ", World!", unit.greet("World"));
    }

    @Test
    @GradedTest(name = "prints greeting", points = 2.0)
    public void printGreeting() {
        PrintStream realStdout = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        unit.printGreeting();
        assertEquals(GREETING, baos.toString().trim());
        System.setOut(realStdout);
    }
}
