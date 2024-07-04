package tesi.unibo.dynamic.library;

import java.util.HashMap;
import java.util.Map;

public class Biblioteca {
    
    private Map<String, Integer> libriDisponibili;

    public Biblioteca() {
        libriDisponibili = new HashMap<>();
    }

    public void aggiungiLibro(String titolo) {
        libriDisponibili.put(titolo, libriDisponibili.getOrDefault(titolo, 0) + 1);
    }

    public boolean prestaLibro(String titolo) {
        if (libriDisponibili.containsKey(titolo) && libriDisponibili.get(titolo) > 0) {
            libriDisponibili.put(titolo, libriDisponibili.get(titolo) - 1);
            return true;
        } else {
            return false;
        }
    }

    public boolean restituisceLibro(String titolo) {
        if (libriDisponibili.containsKey(titolo)) {
            libriDisponibili.put(titolo, libriDisponibili.get(titolo) + 1);
            return true;
        } else {
            return false;
        }
    }
}