package tesi.unibo.controller.impl;

import java.io.IOException;
import tesi.unibo.controller.api.Controller;
import tesi.unibo.elaborator.api.Elaborator;
import tesi.unibo.elaborator.impl.ElaboratorImpl;
import tesi.unibo.generator.api.Generator;
import tesi.unibo.generator.impl.GeneratorJson;
import tesi.unibo.llmcomunication.api.Comunicator;
import tesi.unibo.llmcomunication.impl.ChatGPTComunicator;
import tesi.unibo.reader.api.Reader;
import tesi.unibo.reader.impl.ReaderFromJson;
import tesi.unibo.tester.api.Tester;
import tesi.unibo.tester.impl.TesterJava;
import java.util.Map;
import java.util.HashMap;

public class BasicController implements Controller {
    private static final String URL_RESOURCE = "tests.json";
    private final Comunicator comunicator;
    private final Generator generator;
    private Class<?> textClass;
    private final Tester tester;
    private final String testFileContent;
    private final Reader reader;
    private final Elaborator elaborator;
    private static final String PACKAGE_CLASS = "tesi.unibo.dynamic";
    private static final String PACKAGE_TEST = "tesi.unibo.dynamic.impl";


    public BasicController () {
        comunicator = new ChatGPTComunicator();
        generator = new GeneratorJson(PACKAGE_TEST, PACKAGE_CLASS);
        tester = new TesterJava();
        reader = new ReaderFromJson();
        elaborator = new ElaboratorImpl();
        String dataFile = "";
        try  {
            dataFile = reader.readFromFIle(URL_RESOURCE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        testFileContent = generator.generateTestFileContent(dataFile);
    }

    @Override
    public void play() {
        Map<String, String> logMap = new HashMap<>();
        generateClass(logMap);
        try {
            textClass = generator.generateTest(testFileContent);
        } catch (Exception e) {
            System.out.println("ERROR! invalid test");
            System.exit(1);
        }
        logMap = tester.test(textClass);
        while (!logMap.isEmpty()) {
            generateClass(logMap);
            logMap = tester.test(textClass);
        }
    }

    private void generateClass(final Map<String, String> logMap) {
        final String question = elaborator.elaborateQuestion(logMap);
        final String response = comunicator.generateCode(testFileContent + question);
        try {
            generator.generateClass(response);
        } catch (IOException e) {
            System.out.println("ERROR! invalid class");
            System.exit(1);
        }
    }
    
}
