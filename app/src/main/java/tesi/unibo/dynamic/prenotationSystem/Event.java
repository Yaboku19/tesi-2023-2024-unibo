package tesi.unibo.dynamic.prenotationSystem;

import java.util.Date;

public class Event {
    private String name;
    private Date date;
    private int capacity;

    public Event(String name, Date date, int capacity) {
        this.name = name;
        this.date = date;
        this.capacity = capacity;
    }

    public Event(String name, Date date) {
        this(name, date, Integer.MAX_VALUE);
    }

    public String getName() {
        return this.name;
    }

    public Date getDate() {
        return this.date;
    }

    public int getCapacity() {
        return capacity;
    }
}
