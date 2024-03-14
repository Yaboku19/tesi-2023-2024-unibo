package tesi.unibo.tester.impl;

import java.lang.reflect.InvocationTargetException;

import tesi.unibo.dynamic.api.DynamicTest;
import tesi.unibo.tester.api.Tester;

public class TesterJava implements Tester {

    @Override
    public String test(Class<DynamicTest> testClass) {
        /*Result result = JUnitCore.runClasses(testClass);
        // Gestisci il risultato dei test
        if (result.wasSuccessful()) {
            System.out.println("Tutti i test sono passati!");
        } else {
            System.out.println("Alcuni test sono falliti:");
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }*/
        try {
            var instance = testClass.getConstructor().newInstance();
            testClass.getMethod("launcher").invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException | IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
    
}
