package tesi.unibo.controller.impl;

import org.junit.jupiter.api.DynamicTest;

import tesi.unibo.controller.api.Controller;
import tesi.unibo.generator.api.Generator;
import tesi.unibo.generator.impl.GeneratorJson;
import tesi.unibo.llmcomunication.api.Comunicator;
import tesi.unibo.llmcomunication.impl.ChatGPTComunicator;
import tesi.unibo.tester.api.Tester;
import tesi.unibo.tester.impl.TesterJava;

public class BasicController implements Controller {
    private final Comunicator comunicator;
    private final Generator generator;
    private final Class<DynamicTest> textClass;
    private final Tester tester;

    public BasicController () {
        comunicator = new ChatGPTComunicator();
        generator = new GeneratorJson();
        textClass = generator.generateTest("tests.json");
        tester = new TesterJava();
    }

    @Override
    public void play() {
        //System.out.println(comunicator.generateCode("ho fame"));
        tester.test(textClass);
    }
    
}
