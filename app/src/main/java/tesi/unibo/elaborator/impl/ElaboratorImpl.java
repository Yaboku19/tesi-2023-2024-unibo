package tesi.unibo.elaborator.impl;

import java.util.Map;
import tesi.unibo.elaborator.api.Elaborator;

public class ElaboratorImpl implements Elaborator {
    private final String defaultQuestion;
    private String previousClassJava = "";
    private int counter = 0;
    private final static Double THRESHOLD = 0.03;
    private final static int MAX_SIMILARITY = 3;

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
            final String supporterClass, final String interfaceClass
        ) {
        final StringBuilder content = new StringBuilder();
        content.append(testClassQuestion(testClass));
        if(supporterClass != "") {
            content.append(supporterClassQuestion(supporterClass));
        }
        if (interfaceClass != "") {
            content.append(interfaceClassQuestion(interfaceClass));
        }
        String moreInformation = "";
        if (!logMap.isEmpty()) {
            if (areSimilar(this.previousClassJava, classJava, THRESHOLD)) {
                counter++;
            } else {
                this.previousClassJava = classJava;
                counter = 0;
            }
            if (counter < MAX_SIMILARITY) {
                /*content.append(classJavaQuestion(classJava));
                content.append(logMapQuestion(logMap));
                moreInformation = "\n you have to improve the previous implementation in order to pass all the test. Put particolar attention on the failed tests." +
                    " Read the comment written near the failed test and change the implementation in order to follow the tips";*/
            }
        }
        content.append("\n'\n").append(defaultQuestion).append(moreInformation);
        return content.toString();
    }

    @Override
    public String elaborateCompileError(
        final String compileError, final String classJava, final String testClass, 
        final String supporterClass, final String interfaceClass
    ) {
        final StringBuilder content = new StringBuilder();
        content.append(testClassQuestion(testClass));
        if(supporterClass != "") {
            content.append(supporterClassQuestion(supporterClass));
        }
        if (interfaceClass != "") {
            content.append(interfaceClassQuestion(interfaceClass));
        }
        content.append(classJavaQuestion(classJava));
        this.previousClassJava = classJava;
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
        content.append("\n'\nGiven the following support class, it is already exist, you don't have to write it:\n'\n").append(supporterClass);
        return content.toString();
    }

    private String interfaceClassQuestion(final String interfaceClass) {
        final StringBuilder content = new StringBuilder();
        content.append("\n'\nGiven the following class to implement:\n'\n").append(interfaceClass);
        return content.toString();
    }

    private static int levenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(
                            dp[i - 1][j - 1] + costOfSubstitution(s1.charAt(i - 1), s2.charAt(j - 1)),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1);
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }

    private static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    private static int min(int... numbers) {
        return java.util.Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
    }

    private static boolean areSimilar(String s1, String s2, double threshold) {
        int maxLength = Math.max(s1.length(), s2.length());
        if (maxLength == 0) { return true; }  // Both strings are empty
        int distance = levenshteinDistance(s1, s2);
        double similarity = (maxLength - distance) / (double) maxLength;
        return similarity >= (1.0 - threshold);
    }
    
}
