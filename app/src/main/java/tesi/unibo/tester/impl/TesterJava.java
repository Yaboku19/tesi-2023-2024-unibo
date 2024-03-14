package tesi.unibo.tester.impl;

import org.junit.jupiter.api.DynamicTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import tesi.unibo.tester.api.Tester;

public class TesterJava implements Tester {

    @Override
    public String test(Class<DynamicTest> testClass) {
        for (var method : testClass.getMethods()) {
            System.out.println(method.getName());
        }
        Result result = JUnitCore.runClasses(testClass);
        // Gestisci il risultato dei test
        if (result.wasSuccessful()) {
            System.out.println("Tutti i test sono passati!");
        } else {
            System.out.println("Alcuni test sono falliti:");
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
        return "";
    }
    
}
