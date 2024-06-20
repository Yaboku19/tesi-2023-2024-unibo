package tesi.unibo.tester.impl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SampleTest {

    @Test
    void testSuccess() {
        assertTrue(true);
    }

    @Test
    void testFailure() {
        assertTrue(false, "It has to be false");
    }

    @Test
    void testException() {
        throw new RuntimeException("Test exception");
    }
}
