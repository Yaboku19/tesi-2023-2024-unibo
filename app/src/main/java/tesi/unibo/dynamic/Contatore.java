package tesi.unibo.dynamic;

public class Contatore {
    private int valore;
    
    public Contatore() {
        valore = 0;
    }
    
    public void incrementa() {
        valore++;
    }
    
    public void decrementa() {
        valore--;
    }
    
    public int getValore() {
        return valore;
    }
}