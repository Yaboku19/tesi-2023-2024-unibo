package tesi.unibo.elaborator.impl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

public class ElaboratorTest {
    private ElaboratorImpl elaborator;
    private static final String PACKAGE_CLASS = "tesi.unibo.dynamic";
    private static final String PACKAGE_TEST = "tesi.unibo.dynamic";

    @BeforeEach
    public void setUp() {
        elaborator = new ElaboratorImpl(PACKAGE_CLASS, PACKAGE_TEST);
    }

    @Test
    public void testElaborateQuestion() {
        String testClass = "public class TestExampleClass {}";
        String question = elaborator.elaborateQuestion(new HashMap<>(), "", testClass, "", "");
        assertTrue(question.contains("I want you to generate a Java class named"));
        assertTrue(question.contains("Given the following test class:"));
        assertFalse(question.contains("Given the following support class:"));
        assertFalse(question.contains("Given the following class to implement:"));
        assertFalse(question.contains("Given your previous implementation:"));
        assertFalse(question.contains("Given the following tests failed by your previous implementation:"));
        

        String supporterClass = "public class SupporterClass {}";
        question = elaborator.elaborateQuestion(new HashMap<>(), "", testClass, supporterClass, "");
        assertTrue(question.contains("Given the following support class:"));

        String interfaceClass = "public interface ExampleInterface {}";
        question = elaborator.elaborateQuestion(new HashMap<>(), "", testClass, supporterClass, interfaceClass);
        assertTrue(question.contains("Given the following class to implement:"));

        Map<String, String> logMap = new HashMap<>();
        logMap.put("test1", "failure1");
        String classJava = "public class ExampleClass {}";
        question = elaborator.elaborateQuestion(logMap, classJava, testClass, supporterClass, interfaceClass);
        assertTrue(question.contains("Given your previous implementation:"));
        assertTrue(question.contains("Given the following tests failed by your previous implementation:"));
    }

    @Test
    public void testElaborateCompileError() {
        String compileError = "error1";
        String classJava = "public class ExampleClass {}";
        String testClass = "public class TestExampleClass {}";
        String question = elaborator.elaborateCompileError(compileError, classJava, testClass, "", "");
        assertTrue(question.contains("I want you to generate a Java class named"));
        assertTrue(question.contains("Given the following test class:"));
        assertTrue(question.contains("Given the following compilation errors:"));
        assertTrue(question.contains("Given your previous implementation:"));
        assertFalse(question.contains("Given the following class to implement:"));
        assertFalse(question.contains("Given the following support class:"));
        

        String supporterClass = "public class SupporterClass {}";
        question = elaborator.elaborateCompileError(compileError, classJava, testClass, supporterClass, "");
        assertTrue(question.contains("Given the following support class:"));

        String interfaceClass = "public interface ExampleInterface {}";
        question = elaborator.elaborateCompileError(compileError, classJava, testClass, supporterClass, interfaceClass);
        assertTrue(question.contains("Given the following class to implement:"));
    }
}
