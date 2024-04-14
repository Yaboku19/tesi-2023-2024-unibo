package tesi.unibo.generator.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public interface Generator {

    Class<?> generateTest(String data) throws IOException, ClassNotFoundException;

    int generateClass(String data) throws IOException;

    String generateTestFileContent(final Map<String, String> data);

    static File generateFile(final String textPath, final String pacage, final String className, final String extension, final String textFileContent) throws IOException {
        final File testFile = new File(textPath + pacage.replace(".", "/") + "/" + className + extension);
        Files.writeString(testFile.toPath(), textFileContent);
        return testFile;
    }
}