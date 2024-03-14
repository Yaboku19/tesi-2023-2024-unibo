package tesi.unibo.tester.api;

import tesi.unibo.dynamic.api.DynamicTest;

public interface Tester {
    String test(Class<DynamicTest> testClass);
}
