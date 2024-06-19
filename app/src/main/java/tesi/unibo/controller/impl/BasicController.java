package tesi.unibo.controller.impl;

import java.io.IOException;
import tesi.unibo.controller.api.Controller;
import tesi.unibo.elaborator.api.Elaborator;
import tesi.unibo.elaborator.impl.ElaboratorImpl;
import tesi.unibo.generator.api.Generator;
import tesi.unibo.generator.impl.GeneratorImpl;
import tesi.unibo.llmcomunication.api.Comunicator;
import tesi.unibo.llmcomunication.impl.ChatGPTComunicator;
import tesi.unibo.reader.api.Reader;
import tesi.unibo.reader.impl.ReaderFromYml;
import tesi.unibo.tester.api.Tester;
import tesi.unibo.tester.impl.TesterJava;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class BasicController implements Controller {
    private static final String URL_RESOURCE = "dietTest.yml";
    private static final String PACKAGE_CLASS = "tesi.unibo.dynamic";
    private static final String PACKAGE_TEST = "tesi.unibo.dynamic";
    private static final String TEST_NAME = "DynamicTest";
    private final Comunicator comunicator;
    private final Generator generator;
    private final Tester tester;
    private final String testFileContent;
    private final Reader reader;
    private final Elaborator elaborator;
    private Class<?> testClass;
    private String classJava = "";
    private Queue<Long> callTimes = new LinkedList<>();
    private static final long INTERVAL = 60000;
    private static final int CALL = 500;
    
    public BasicController () {
        this.comunicator = new ChatGPTComunicator();
        this.generator = new GeneratorImpl(PACKAGE_TEST, PACKAGE_CLASS);
        this.tester = new TesterJava();
        this.reader = new ReaderFromYml(PACKAGE_TEST, TEST_NAME);
        
        String dataFile = "";
        try  {
            dataFile = this.reader.readFromFile(URL_RESOURCE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.testFileContent = dataFile;
        elaborator = new ElaboratorImpl(PACKAGE_CLASS, reader.getClassName());
    }

    @Override
    public void play() throws InterruptedException {
        final Map<String, String> logMap = new HashMap<>();
        generateClass(logMap);
        try {
            this.testClass = generator.generateTest(testFileContent, TEST_NAME);
        } catch (Exception e) {
            System.out.println("ERROR! invalid test");
            System.exit(1);
        }
        logMap.clear();
        logMap.putAll(tester.test(testClass));
        while (!logMap.isEmpty()) {
            generateClass(logMap);
            logMap.clear();
            logMap.putAll(tester.test(testClass));
        }
    }

    private void generateClass(final Map<String, String> logMap) throws InterruptedException {
        String question = this.elaborator.elaborateQuestion(logMap, classJava, testFileContent, reader.getSupportClass(),
                                                            reader.getInterfaceClass());
        Thread.sleep(tryCall());
        String compileError = "";
        do {
            System.out.println("question = \n" + question);
            System.out.println("--------------------------------");
            String response = this.comunicator.generateCode( question);
            System.out.println("response = \n" + response);
            System.out.println("--------------------------------");
            setCodeJava(response);
            try {
                compileError = this.generator.generateClass(classJava, reader.getClassName());
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            if (compileError != "") {
                question = this.elaborator.elaborateCompileError(compileError, classJava, testFileContent,
                                                                reader.getSupportClass(), reader.getInterfaceClass());
            }
        }while(compileError != "");
    }

    private void setCodeJava(final String response) {
        classJava = "";
        Pattern pattern = Pattern.compile("```java(.*?)```", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(response);
        while (matcher.find()) {
            classJava += matcher.group(1);
        }
        if (classJava == "") {
            classJava = response;
        }
    }

    public synchronized long tryCall() {
        long currentTime = System.currentTimeMillis();
        while (!callTimes.isEmpty() && (currentTime - callTimes.peek() > INTERVAL)) {
            callTimes.poll();
        }
        if (callTimes.size() < CALL) {
            callTimes.add(currentTime);
            return 0;
        } else {
            long nextValidTime = INTERVAL - (currentTime - callTimes.peek());
            return nextValidTime; 
        }
    }
    
}
