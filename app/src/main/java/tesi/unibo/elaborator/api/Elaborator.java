package tesi.unibo.elaborator.api;

import java.util.Map;

public interface Elaborator {
    
    String elaborateQuestion(Map<String, String> logMap, String classJava, String testClass, String supporterClass, 
                            String interfaceClass);

    String elaborateCompileError(String compileError, String classJava, String testClass, String supporterClass, 
                                String interfaceClass);
}
