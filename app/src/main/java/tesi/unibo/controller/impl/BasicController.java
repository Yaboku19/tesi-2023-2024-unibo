package tesi.unibo.controller.impl;

import tesi.unibo.controller.api.Controller;
import tesi.unibo.generator.api.Generator;
import tesi.unibo.generator.impl.GeneratorJson;
import tesi.unibo.llmcomunication.api.Comunicator;
import tesi.unibo.llmcomunication.impl.ChatGPTComunicator;

public class BasicController implements Controller {
    private final Comunicator comunicator;
    private final Generator generator;
    private final Class<?> textClass;

    public BasicController () {
        comunicator = new ChatGPTComunicator();
        generator = new GeneratorJson();
        textClass = generator.generateTest("tests.json");
    }

    @Override
    public void play() {
        //System.out.println(comunicator.generateCode("ho fame"));
    }
    
}
