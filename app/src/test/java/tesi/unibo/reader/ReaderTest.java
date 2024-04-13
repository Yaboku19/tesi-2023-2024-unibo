package tesi.unibo.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tesi.unibo.reader.api.Reader;
import tesi.unibo.reader.impl.ReaderFromJson;

public class ReaderTest {
    private Reader reader;

    @BeforeEach
    public void setUp() {
        reader = new ReaderFromJson();
    }

    @Test
    public void readWithWrongUrl() {
        String data = "";
        try {
            data = reader.readFromFIle("test.json");
        } catch (Exception e) {
        }
        assertEquals(data, "");
    }

    @Test
    public void readWithRightUrl() {
        String data = "";
        try {
            data = reader.readFromFIle("tests.json");
        } catch (Exception e) {
        }
        assertNotEquals(data, "");
    }

    @Test
    public void readClassName() {
        String data = "";
        try {
            data = reader.readFromFIle("tests.json");
        } catch (Exception e) {
        }
        final String className = new JSONObject(data).getString("class");
        assertEquals(className, reader.getName(data));
    }
}
