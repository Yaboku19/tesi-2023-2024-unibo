package tesi.unibo.dynamic;

public class Contatore {
    private int valore = 0;

    public int getValore() {
        return valore;
    }

    public void incrementa() {
        valore++;
    }

    public void decrementa() {
        valore--;
    }
}