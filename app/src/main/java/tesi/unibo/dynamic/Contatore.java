package tesi.unibo.dynamic;

public class Contatore {
    private int valore;

    public Contatore() {
        this.valore = 0;
    }

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