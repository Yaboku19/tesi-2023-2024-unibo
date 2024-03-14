package tesi.unibo.generator.api;

import org.junit.jupiter.api.DynamicTest;

public interface Generator {
    Class<DynamicTest> generateTest(String url);    
}