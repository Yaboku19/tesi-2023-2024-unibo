package tesi.unibo.reader.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class Reader {
    private final String testName;
    private final String packageTest;
    private String className = "";
    private String supportClassName = "";
    private String interfaceClassName = "";
    private final static String pathToSupportClass = "app/src/main/java/tesi/unibo/dynamic/";

    public Reader(final String pacakageTest, final String testName) {
        this.packageTest = pacakageTest;
        this.testName = testName;
    }

    protected String getTestName() {
        return this.testName;
    }

    protected String getPackageTest() {
        return this.packageTest;
    }

    public String getClassName() {
        return this.className;
    }

    protected void setClassName(final String className) {
        this.className = className;
    }

    public String getSupportClass() {
        String content = "";
        if (supportClassName != "") {
            try {
                final File testFile = new File(pathToSupportClass + supportClassName + ".java");
                content = new String(Files.readAllBytes(Paths.get(testFile.toURI())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    protected void setSupportClassName(final String supportClassName) {
        this.supportClassName = supportClassName;
    }

    protected void setInterfaceClassName(final String implementClassName) {
        this.interfaceClassName = implementClassName;
    }

    public String getInterfaceClass() {
        String content = "";
        if (supportClassName != "") {
            try {
                final File testFile = new File(pathToSupportClass + interfaceClassName + ".java");
                content = new String(Files.readAllBytes(Paths.get(testFile.toURI())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    protected String addTab(final int count) {
        final StringBuilder toReturn = new StringBuilder();
        for (int j = 0; j < count; j++) {
            toReturn.append("\t");
        }
        return toReturn.toString();
    }

    public abstract String readFromFile(final String url) throws Exception;
}
