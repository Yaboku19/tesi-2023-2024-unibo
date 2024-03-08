package tesi.unibo.controller.impl;

import tesi.unibo.controller.api.Controller;
import tesi.unibo.llmcomunication.api.Comunicator;
import tesi.unibo.llmcomunication.impl.ChatGPTComunicator;

public class BasicController implements Controller {
    private final Comunicator comunicator;

    public BasicController () {
        comunicator = new ChatGPTComunicator();
    }

    @Override
    public void play() {
        System.out.println(comunicator.generateCode("Chi sei?"));
    }
    
}
