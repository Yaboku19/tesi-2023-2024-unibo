package tesi.unibo.elaborator.impl;

import java.util.Map;


import tesi.unibo.elaborator.api.Elaborator;

public class ElaboratorImpl implements Elaborator {
    private final String defaultQuestion;
    public ElaboratorImpl(final String packageClass, final String className) {
        this.defaultQuestion = 
            "Voglio che mi generi la classe java di nome " + className +" che passa i test forniti "
            +" il package della classe da creare e' \"" + packageClass + 
            "\". VOGLIO SOLO IL CODICE NO COMMENTI. Voglio del codice runnabile con le informazioni che ti ho dato. Non" +
            "voglio esempi, voglio solo che funzioni e passi i test";
        }

    @Override
    public String elaborateQuestion(final Map<String, String> logMap, final String classJava, final String testClass) {
        final StringBuilder content = new StringBuilder();
        content.append(testClassQuestion(testClass));
        if (!logMap.isEmpty()) {
            content.append(classJavaQuestion(classJava));
            content.append(logMapQuestion(logMap));
        }
        content.append("\n\n").append(defaultQuestion);
        return content.toString();
    }

    @Override
    public String elaberateQuestionWithSupportClass(
            final Map<String, String> logMap, final String classJava, final String testClass, final String supporterClass
            ) {
        final StringBuilder content = new StringBuilder();
        content.append(testClassQuestion(testClass));
        content.append(supporterClassQuestion(supporterClass));
        if (!logMap.isEmpty()) {
            content.append(classJavaQuestion(classJava));
            content.append(logMapQuestion(logMap));
        }
        content.append("\n\n").append(defaultQuestion);
        return content.toString();
    }

    @Override
    public String elaberateQuestionWithSupportAndImplementClass(Map<String, String> logMap, String classJava,
            String testClass, String supporterClass, String implementClass
        ) {
        final StringBuilder content = new StringBuilder();
        content.append(testClassQuestion(testClass));
        content.append(supporterClassQuestion(supporterClass));
        content.append(implementClassQuestion(implementClass));
        if (!logMap.isEmpty()) {
            content.append(classJavaQuestion(classJava));
            content.append(logMapQuestion(logMap));
        }
        content.append("\n\n").append(defaultQuestion);
        return content.toString();
    }

    @Override
    public String elaborateCompileError(String compileError, final String classJava, final String testClass) {
        final StringBuilder content = new StringBuilder();
        content.append(testClassQuestion(testClass));
        content.append(classJavaQuestion(classJava));
        content.append(compileError(compileError));
        content.append(defaultQuestion);
        return content.toString();
    }

    @Override
    public String elaborateCompileErrorWithSupportClass(String compileError, String classJava, String testClass,
            String supporterClass
            ) {
        final StringBuilder content = new StringBuilder();
        content.append(testClassQuestion(testClass));
        content.append(supporterClassQuestion(supporterClass));
        content.append(classJavaQuestion(classJava));
        content.append(compileError(compileError));
        content.append(defaultQuestion);
        return content.toString();
    }

    @Override
    public String elaborateCompileErrorWithSupportAndImplementClass(String compileError, String classJava,
            String testClass, String supporterClass, String implementClass
        ) {
        final StringBuilder content = new StringBuilder();
        content.append(testClassQuestion(testClass));
        content.append(supporterClassQuestion(supporterClass));
        content.append(implementClassQuestion(implementClass));
        content.append(classJavaQuestion(classJava));
        content.append(compileError(compileError));
        content.append(defaultQuestion);
        return content.toString();
    }

    private String testClassQuestion(final String testClass) {
        final StringBuilder content = new StringBuilder();
        content.append("Data la seguente classe test:\n\n").append(testClass);
        return content.toString();
    }

    private String classJavaQuestion(final String classJava) {
        final StringBuilder content = new StringBuilder();
        content.append("\n\n Data la tua precedente implementazione:\n\n").append(classJava);
        return content.toString();
    }

    private String logMapQuestion(final Map<String, String> logMap) {
        final StringBuilder content = new StringBuilder();
        content.append("\n\n Dati i seguenti test falliti dalla tua precedente implementazione:\n\n");
        for (var entry : logMap.entrySet()) {
            content.append("\t- " + entry.getKey() + ": " + entry.getValue() + "\n");
        }
        return content.toString();
    }

    private String compileError(final String compileError) {
        final StringBuilder content = new StringBuilder();
        content.append("\n\n Dati i seguenti errori di compilazione:\n\n").append(compileError);
        return content.toString();
    }

    private String supporterClassQuestion(final String supporterClass) {
        final StringBuilder content = new StringBuilder();
        content.append("\n\n Data la seguente classe di supporto:\n\n").append(supporterClass);
        return content.toString();
    }

    private String implementClassQuestion(final String implementClass) {
        final StringBuilder content = new StringBuilder();
        content.append("\n\n Data la seguente classe da implementare:\n\n").append(implementClass);
        return content.toString();
    }
    
}
