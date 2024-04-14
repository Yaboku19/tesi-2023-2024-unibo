package tesi.unibo.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tesi.unibo.reader.api.Reader;
import tesi.unibo.reader.impl.ReaderFromJson;

public class ReaderTest {
    private Reader reader;

    @BeforeEach
    public void setUp() {
        reader = new ReaderFromJson("tesi.unibo.dynamic", "DynamicTest");
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
}
