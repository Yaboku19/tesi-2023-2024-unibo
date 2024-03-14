package tesi.unibo.generator.api;

import tesi.unibo.dynamic.api.DynamicTest;

public interface Generator {
    Class<DynamicTest> generateTest(String url);    
}