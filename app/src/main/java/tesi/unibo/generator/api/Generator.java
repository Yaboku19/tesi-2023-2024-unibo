package tesi.unibo.generator.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public interface Generator {

    Class<?> generateTest(String data);

    void generateClass(String data);

    String generateTestFileContent(final String data);

    static File generateFile(final String textPath, final String pacage, final String className, final String extension, final String textFileContent) throws IOException {
        System.out.println(textFileContent);
        final File testFile = new File(textPath + pacage.replace(".", "/") + "/" + className + extension);
        Files.writeString(testFile.toPath(), textFileContent);
        return testFile;
    }
}