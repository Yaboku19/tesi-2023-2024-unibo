package tesi.unibo;

import tesi.unibo.controller.impl.BasicController;

public class App {

    public static void main(String[] args) {
        try {
            new BasicController().play();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
