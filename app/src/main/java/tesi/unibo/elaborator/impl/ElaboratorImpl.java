package tesi.unibo.elaborator.impl;

import java.util.Map;


import tesi.unibo.elaborator.api.Elaborator;

public class ElaboratorImpl implements Elaborator {
    private final String defaultQuestion;
    public ElaboratorImpl(final String packageClass, final String className) {
        this.defaultQuestion = "\n\n Ti ho mandato una classe test in java. Voglio che mi generi la classe java di nome " + className +
                                ", la classe non implementa e non estende nulla, che passa i test forniti, il package della classe da creare e' \"" 
                                + packageClass + "\". VOGLIO SOLO IL CODICE NO COMMENTI. Devi implementare te tutti i passaggi";
        }

    @Override
    public String elaborateQuestion(final Map<String, String> logMap, final String classJava) {
        if (logMap.isEmpty()) {
            return defaultQuestion;
        } else {
            final StringBuilder content = new StringBuilder();
            content.append(defaultQuestion);
            content.append("Questa era la tua precedente implementazione : \n\n").append(classJava);
            content.append("\n\nDalla tua precedente implementazione i seguenti test sono falliti:\n");
            for (var entry : logMap.entrySet()) {
                content.append("\t- " + entry.getKey() + ": " + entry.getValue() + "\n");
            }
            content.append("Rigenera la classe tenendo conto dei test falliti");
            return content.toString();
        }
        
    }

    @Override
    public String elaborateCompileError(String compileError, final String classJava) {
        final StringBuilder content = new StringBuilder();
        content.append(defaultQuestion + "\n" + "Questa è stata la tua implementazione\n");
        content.append(classJava).append("\n\n");
        content.append("Ha dato i seguenti problemi \n\n");
        content.append(compileError);
        return content.toString();
    }
    
}
