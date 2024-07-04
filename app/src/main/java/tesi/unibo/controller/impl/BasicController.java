package tesi.unibo.controller.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

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

import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class BasicController implements Controller {
    private static final String URL_SETTINGS = "config/config.yml";
    private final String testName;
    private final Comunicator comunicator;
    private final Generator generator;
    private final Tester tester;
    private final String testFileContent;
    private final Reader reader;
    private final Elaborator elaborator;
    private Class<?> testClass;
    private String classJava = "";
    private Queue<Long> callTimes = new LinkedList<>();
    private final static long INTERVAL = 6000;
    private final static int CALL = 500;
    
    public BasicController () {
        Map<String, String> file = settings();
        final String urlResource = file.get("urlResources");
        final String packageClass = file.get("packageClass");
        final String packageTest = file.get("packageTest");
        testName = file.get("testName");
        this.comunicator = new ChatGPTComunicator(file.get("keyGPT"), file.get("model"));
        this.generator = new GeneratorImpl(packageTest, packageClass);
        this.tester = new TesterJava();
        this.reader = new ReaderFromYml(packageTest, testName);
        
        String dataFile = "";
        try  {
            dataFile = this.reader.readFromFile(urlResource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.testFileContent = dataFile;
        elaborator = new ElaboratorImpl(packageClass, reader.getClassName());
    }

    private Map<String, String> settings() {
        try {
            final InputStream inputStream = new FileInputStream(new File(ClassLoader.getSystemResource(URL_SETTINGS).toURI()));
            return new Yaml().load(inputStream);
        } catch (FileNotFoundException | URISyntaxException e) {
            System.exit(1);
        }
        return new HashMap<>();
    }

    @Override
    public void play() throws InterruptedException {
        final Map<String, String> logMap = new HashMap<>();
        generateClass(logMap);
        try {
            this.testClass = generator.generateTest(testFileContent, testName);
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
