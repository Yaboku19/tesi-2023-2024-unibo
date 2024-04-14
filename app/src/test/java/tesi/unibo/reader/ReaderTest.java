package tesi.unibo.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tesi.unibo.reader.api.Reader;
import tesi.unibo.reader.impl.ReaderFromJson;
import java.util.Map;
import java.util.HashMap;

public class ReaderTest {
    private Reader reader;

    @BeforeEach
    public void setUp() {
        reader = new ReaderFromJson();
    }

    @Test
    public void readWithWrongUrl() {
        Map<String, String> data = new HashMap<>();
        try {
            data = reader.readFromFIle("test.json");
        } catch (Exception e) {
        }
        assertEquals(data, new HashMap<>());
    }

    @Test
    public void readWithRightUrl() {
        Map<String, String> data = new HashMap<>();
        try {
            data = reader.readFromFIle("tests.json");
        } catch (Exception e) {
        }
        assertNotEquals(data, new HashMap<>());
    }
}
