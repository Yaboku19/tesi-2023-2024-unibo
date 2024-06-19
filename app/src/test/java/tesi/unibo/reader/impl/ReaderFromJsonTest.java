package tesi.unibo.reader.impl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReaderFromJsonTest {
    private static final String PACKAGE_TEST = "tesi.unibo.dynamic";
    private static final String TEST_NAME = "DynamicTest";
    private static final String URL_RESOURCE = "tests.json";
    private static final String CLASS_NAME = "DietFactoryImpl";
    private ReaderFromJson reader;

    @BeforeEach
    public void setUp() {
        reader = new ReaderFromJson(PACKAGE_TEST, TEST_NAME);
    }

    @Test
    public void testReadFromFile() throws Exception {
        String content = reader.readFromFile(URL_RESOURCE);
        assertNotNull(content, "Il contenuto non dovrebbe essere null");
    }

    @Test
    public void testGetSupportClassVoid() throws Exception {
        String supportClassContent = reader.getSupportClass();
        assertEquals(supportClassContent, "");
    }

    @Test
    public void testGetInterfaceClassVoid() throws Exception {
        String interfaceClassContent = reader.getInterfaceClass();
        assertEquals(interfaceClassContent, "");
    }

    @Test
    public void setName() {
        assertEquals(reader.getClassName(), "");
        try {
            reader.readFromFile("tests.json");
        } catch (Exception e) {
        }
        assertNotEquals(reader.getClassName(), CLASS_NAME);
    }
}