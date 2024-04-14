package tesi.unibo.elaborator.impl;

import java.util.Map;


import tesi.unibo.elaborator.api.Elaborator;

public class ElaboratorImpl implements Elaborator {
    private final String defaultQuestion;

    public ElaboratorImpl(final String packageClass) {
        this.defaultQuestion = "\n\n Ho scritto una classe di test java. Voglio che mi generi una classe java che passa i test forniti, " +
                                "il package della classe da creare e' \"" + packageClass + "\". VOGLIO SOLO IL CODICE NO COMMENTI";
    }
    @Override
    public String elaborateQuestion(Map<String, String> logMap) {
        if (logMap.isEmpty()) {
            return defaultQuestion;
        } else {
            final StringBuilder content = new StringBuilder();
            content.append(defaultQuestion);
            content.append("\nDalla tua precedente implementazione i seguenti test sono falliti:\n");
            for (var entry : logMap.entrySet()) {
                content.append("\t- " + entry.getKey() + ": " + entry.getValue() + "\n");
            }
            content.append("Rigenera la classe tenendo conto dei test falliti");
            return content.toString();
        }
        
    }
    
}
