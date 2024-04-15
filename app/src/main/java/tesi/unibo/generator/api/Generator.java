package tesi.unibo.generator.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public interface Generator {

    Class<?> generateTest(String data, String testName) throws IOException, ClassNotFoundException;

    int generateClass(String data, String className) throws IOException;

    static File generateFile(final String textPath, final String pacage, final String className, final String extension, final String textFileContent) throws IOException {
        final File testFile = new File(textPath + pacage.replace(".", "/") + "/" + className + extension);
        Files.writeString(testFile.toPath(), textFileContent);
        return testFile;
    }
}