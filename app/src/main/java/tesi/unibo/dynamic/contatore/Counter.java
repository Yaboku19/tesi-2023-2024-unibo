package tesi.unibo.dynamic.contatore;

public class Counter {
    private int valore;

    public Counter() {
        this.valore = 0;
    }

    public void increment() {
        this.valore++;
    }

    public void decrement() {
        this.valore--;
    }

    public int getValue() {
        return this.valore;
    }
}