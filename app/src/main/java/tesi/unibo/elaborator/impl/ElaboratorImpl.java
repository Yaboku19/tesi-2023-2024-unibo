package tesi.unibo.elaborator.impl;

import java.util.Map;


import tesi.unibo.elaborator.api.Elaborator;

public class ElaboratorImpl implements Elaborator {
    private final String defaultQuestion;
    public ElaboratorImpl(final String packageClass, final String className) {
        this.defaultQuestion = 
            "I want you to generate a Java class named " + className +" that passes the provided tests. "
            +" The package for the class to be created is \"" + packageClass + 
            "\". I WANT ONLY THE CODE, NO COMMENTS. I want runnable code with the information I've given you." +
            " I don't want examples, I just want it to work and pass the tests.";
        }

    @Override
    public String elaborateQuestion(
            final Map<String, String> logMap, final String classJava, final String testClass,
            final String supporterClass, final String implementClass
        ) {
        final StringBuilder content = new StringBuilder();
        content.append(testClassQuestion(testClass));
        if(supporterClass != "") {
            content.append(supporterClassQuestion(supporterClass));
        }
        if (implementClass != "") {
            content.append(implementClassQuestion(implementClass));
        }
        if (!logMap.isEmpty()) {
            content.append(classJavaQuestion(classJava));
            content.append(logMapQuestion(logMap));
        }
        content.append("\n'\n").append(defaultQuestion);
        return content.toString();
    }

    @Override
    public String elaborateCompileError(
        final String compileError, final String classJava, final String testClass, 
        final String supporterClass, final String implementClass
    ) {
        final StringBuilder content = new StringBuilder();
        content.append(testClassQuestion(testClass));
        if(supporterClass != "") {
            content.append(supporterClassQuestion(supporterClass));
        }
        if (implementClass != "") {
            content.append(implementClassQuestion(implementClass));
        }
        content.append(classJavaQuestion(classJava));
        content.append(compileError(compileError));
        content.append("\n'\n").append(defaultQuestion);
        return content.toString();
    }

    private String testClassQuestion(final String testClass) {
        final StringBuilder content = new StringBuilder();
        content.append("Given the following test class:\n'\n").append(testClass);
        return content.toString();
    }

    private String classJavaQuestion(final String classJava) {
        final StringBuilder content = new StringBuilder();
        content.append("\n'\nGiven your previous implementation:\n'\n").append(classJava);
        return content.toString();
    }

    private String logMapQuestion(final Map<String, String> logMap) {
        final StringBuilder content = new StringBuilder();
        content.append("\n'\nGiven the following tests failed by your previous implementation:\n'\n");
        for (var entry : logMap.entrySet()) {
            content.append("\t- " + entry.getKey() + ": " + entry.getValue() + "\n");
        }
        return content.toString();
    }

    private String compileError(final String compileError) {
        final StringBuilder content = new StringBuilder();
        content.append("\n'\nGiven the following compilation errors:\n'\n").append(compileError);
        return content.toString();
    }

    private String supporterClassQuestion(final String supporterClass) {
        final StringBuilder content = new StringBuilder();
        content.append("\n'\nGiven the following support class:\n'\n").append(supporterClass);
        return content.toString();
    }

    private String implementClassQuestion(final String implementClass) {
        final StringBuilder content = new StringBuilder();
        content.append("\n'\nGiven the following class to implement:\n'\n").append(implementClass);
        return content.toString();
    }
    
}
