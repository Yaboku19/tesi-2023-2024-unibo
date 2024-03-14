package tesi.unibo.tester.api;

import org.junit.jupiter.api.DynamicTest;

public interface Tester {
    String test(Class<DynamicTest> testClass);
}
