package tesi.unibo.tester.impl;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import tesi.unibo.tester.api.Tester;

public class TesterJava implements Tester {

    @Override
    public Map<String, String> test(Class<?> testClass) {
        Result result = JUnitCore.runClasses(testClass);
        final Map<String, String> logMap = new HashMap<>();
        // Gestisci il risultato dei test
        if (!result.wasSuccessful()) {
            for (Failure failure : result.getFailures()) {
                logMap.put(failure.getDescription().toString(), failure.getMessage());
            } 
        }
        return logMap;
    }
    
}
