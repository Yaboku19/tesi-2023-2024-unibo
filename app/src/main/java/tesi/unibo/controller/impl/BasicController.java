package tesi.unibo.controller.impl;

import java.io.IOException;
import tesi.unibo.controller.api.Controller;
import tesi.unibo.generator.api.Generator;
import tesi.unibo.generator.impl.GeneratorJson;
import tesi.unibo.llmcomunication.api.Comunicator;
import tesi.unibo.llmcomunication.impl.ChatGPTComunicator;
import tesi.unibo.reader.api.Reader;
import tesi.unibo.reader.impl.ReaderFromJson;
import tesi.unibo.tester.api.Tester;
import tesi.unibo.tester.impl.TesterJava;

public class BasicController implements Controller {
    private static final String URL_RESOURCE = "tests.json";
    private final Comunicator comunicator;
    private final Generator generator;
    private Class<?> textClass;
    private final Tester tester;
    private final String testData;
    private final String testFileContent;
    private final Reader reader;

    public BasicController () {
        comunicator = new ChatGPTComunicator();
        generator = new GeneratorJson();
        tester = new TesterJava();
        reader = new ReaderFromJson();
        String dataFile = "";
        try  {
            dataFile = reader.readFromFIle(URL_RESOURCE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        testData = dataFile;
        testFileContent = generator.generateTestFileContent(dataFile);
    }

    @Override
    public void play() {
        generateClass();
        //textClass = generator.generateTest(testFileContent);
    }

    private void generateClass() {
        generator.generateClass(
            comunicator.generateCode(testFileContent
            + "\n\nti ho scritto una classe in java con dei test. Voglio che mi generi una classe che passa i test. SCRIVI SOLO IL CODICE JAVA, senza commenti"));
    }
    
}
