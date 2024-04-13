package tesi.unibo.elaborator.impl;

import java.util.Map;

import tesi.unibo.elaborator.api.Elaborator;

public class ElaboratorImpl implements Elaborator {
    private final String defaultQuestion = "\n\nti ho scritto una classe in java con dei test. Voglio che mi generi una classe che passa i test, " +
                                            "il package della clasee è \"tesi.unibo.dynamic\". VOGLIO SOLO IL CODICE NO COMMENTI";

    @Override
    public String elaborateQuestion(Map<String, String> logMap) {
        if (logMap.isEmpty()) {
            return defaultQuestion;
        }
        return "";
    }
    
}
