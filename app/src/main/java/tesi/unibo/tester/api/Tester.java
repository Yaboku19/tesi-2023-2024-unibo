package tesi.unibo.tester.api;

import java.util.Map;

public interface Tester {

    Map<String, String> test(Class<?> testClass);
}
