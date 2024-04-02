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
    private final Class<?> textClass;
    private final Tester tester;
    private final String testData;

    public BasicController () {
        comunicator = new ChatGPTComunicator();
        generator = new GeneratorJson();
        tester = new TesterJava();
        final Reader reader = new ReaderFromJson();
        String dataFile = "";
        try  {
            dataFile = reader.readFromFIle(URL_RESOURCE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        testData = dataFile;
        textClass = generator.generateTest(dataFile);
    }

    @Override
    public void play() {
        //System.out.println(comunicator.generateCode("ho fame"));
        System.out.println(tester.test(textClass));
    }
    
}
