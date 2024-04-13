package tesi.unibo.dynamic;

public class Contatore {
    private int valore;

    public Contatore() {
        this.valore = 0;
    }

    public void incrementa() {
        this.valore++;
    }

    public void decrementa() {
        this.valore--;
    }

    public int getValore() {
        return this.valore;
    }
}