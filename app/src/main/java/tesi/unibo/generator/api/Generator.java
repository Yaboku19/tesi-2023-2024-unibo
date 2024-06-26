package tesi.unibo.generator.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.io.FileOutputStream;

public interface Generator {

    Class<?> generateTest(String data, String testName) throws IOException, ClassNotFoundException;

    String generateClass(String data, String className) throws IOException;

    static File generateFile(final String textPath, final String pakage, final String className, final String extension, final String textFileContent) throws IOException {
        final File testFile = new File(textPath + pakage.replace(".", "/") + "/" + className + extension);

        testFile.getParentFile().mkdirs();

        if (testFile.exists()) {
            new FileOutputStream(testFile).close(); 
        }
        Files.writeString(testFile.toPath(), textFileContent, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    
        return testFile;
    }
}