package tesi.unibo.llmcomunication.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tesi.unibo.llmcomunication.api.Comunicator;

import static org.junit.jupiter.api.Assertions.*;

public class comunicatorGPTtest {

    private Comunicator comunicator;

    @BeforeEach
    void setUp() {
        comunicator = new ChatGPTComunicator();
    }

    @Test
    void testGenerateCodeWithValidQuestion() {
        String question = "Write a Java function to reverse a string.";
        String result = comunicator.generateCode(question);

        assertNotNull(result, "The result should not be null");
        assertFalse(result.isEmpty(), "The result should not be empty");
        assertTrue(result.contains("reverse"), "The result should contain 'reverse'");
    }

    @Test
    void testGenerateCodeWithEmptyQuestion() {
        String question = "";
        String result = comunicator.generateCode(question);

        assertNotNull(result, "The result should not be null");
        assertFalse(result.isEmpty(), "The result should not be empty");
    }

    @Test
    void testGenerateCodeWithComplexQuestion() {
        String question = "Generate a Java class that implements a simple HTTP server.";
        String result = comunicator.generateCode(question);

        assertNotNull(result, "The result should not be null");
        assertFalse(result.isEmpty(), "The result should not be empty");
        assertTrue(result.contains("class"), "The result should contain 'class'");
        assertTrue(result.contains("HTTP"), "The result should contain 'HTTP'");
    }
}
