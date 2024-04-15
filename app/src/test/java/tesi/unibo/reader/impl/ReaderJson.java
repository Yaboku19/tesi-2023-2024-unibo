package tesi.unibo.reader.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReaderJson {
    private ReaderFromJson reader;

    @BeforeEach
    public void setUp() {
        reader = new ReaderFromJson("tesi.unibo.dynamic", "DynamicTest");
    }

    @Test
    public void readWithWrongUrl() {
        String data = "";
        try {
            data = reader.readFromFile("test.json");
        } catch (Exception e) {
        }
        assertEquals(data, "");
    }

    @Test
    public void readWithRightUrl() {
        String data = "";
        try {
            data = reader.readFromFile("tests.json");
        } catch (Exception e) {
        }
        assertNotEquals(data, "");
    }

    @Test
    public void setName() {
        assertEquals(reader.getClassName(), "");
        try {
            reader.readFromFile("tests.json");
        } catch (Exception e) {
        }
        assertNotEquals(reader.getClassName(), "");
    }
}
