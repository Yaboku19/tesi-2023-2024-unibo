package tesi.unibo.elaborator.api;

import java.util.Map;

public interface Elaborator {
    
    String elaborateQuestion(Map<String, String> logMap, String classJava, String testClass);

    String elaberateQuestionWithSupportClass(Map<String, String> logMap, String classJava, String testClass,
                                             String supporterClass);

    String elaberateQuestionWithSupportAndImplementClass(Map<String, String> logMap, String classJava, String testClass, 
                                                        String supporterClass, String implementClass);

    String elaborateCompileError(String compileError, String classJava, String testClass);

    String elaborateCompileErrorWithSupportClass(String compileError, String classJava, String testClass, 
                                                    String supporterClass);

    String elaborateCompileErrorWithSupportAndImplementClass(String compileError, String classJava, String testClass, 
                                                            String supporterClass, String implementClass);
}
