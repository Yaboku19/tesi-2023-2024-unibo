package tesi.unibo.elaborator.api;

import java.util.Map;

public interface Elaborator {
    
    String elaborateQuestion(Map<String, String> logMap, String classJava);

    String elaberateQuestionWithClass(Map<String, String> logMap, String classJava, String supporterClass);

    String elaborateCompileError(String compileError, String classJava);
}
