package tesi.unibo.tester.impl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tesi.unibo.tester.api.Tester;

import java.util.Map;

public class TesterTest {

    @Test
    void testWithSampleTestClass() {
        // Creare un'istanza di TesterJava
        Tester tester = new TesterJava();

        // Eseguire i test sulla classe di esempio
        Map<String, String> result = tester.test(tesi.unibo.tester.impl.SampleTest.class);

        // Verificare i risultati
        assertNotNull(result, "Result map should not be null");
        assertEquals(2, result.size(), "There should be 2 failures");

        // Verificare che i nomi dei test e i messaggi di errore siano corretti
        assertTrue(result.containsKey("testFailure()"), "Result should contain testFailure()");
        assertTrue(result.get("testFailure()").contains("expected: <true> but was: <false>"));
        assertTrue(result.get("testFailure()").contains("It has to be false"));

        assertTrue(result.containsKey("testException()"), "Result should contain testException()");
        assertEquals("Test exception", result.get("testException()"));
    }
}

