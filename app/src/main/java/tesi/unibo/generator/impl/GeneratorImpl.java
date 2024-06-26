package tesi.unibo.generator.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import tesi.unibo.generator.api.Generator;

public class GeneratorImpl implements Generator {
    private static final String TEXT_PATH = "app/src/test/java/";
    private static final String CLASS_PATH = "app/src/main/java/";
    private static final String EXTENSION = ".java";
    private static final String BUILD_PATH_TEST = "/app/build/classes/java/test";
    private static final String BUILD_PATH_CLASS = "/app/build/classes/java/main";
    private final String packageTest;
    private final String packageClass;

    public GeneratorImpl(final String packageTest, final String packageClass) {
        this.packageTest = packageTest;
        this.packageClass = packageClass;
    }
    
    @Override
    public Class<?> generateTest(final String testFileContent, final String testName) throws IOException, ClassNotFoundException {
        final File testFile = Generator.generateFile(TEXT_PATH, packageTest, testName, EXTENSION, testFileContent);

        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result = compiler.run(null, null, null, "-d",
                        "." + BUILD_PATH_TEST, testFile.getAbsolutePath());

        final URL testUrl = new File(System.getProperty("user.dir") + BUILD_PATH_TEST).toURI().toURL();
        final URLClassLoader testClassLoader = URLClassLoader.newInstance(new URL[]{testUrl});
        if (result != 0) {
            System.exit(result);
        }
        return testClassLoader.loadClass(packageTest + "." + testName);
    }

    public String generateClass(final String data, final String className) throws IOException {
        final File testFile = Generator.generateFile(CLASS_PATH, packageClass, className, EXTENSION, data);
        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        final int result = compiler.run(null, null, errorStream, "-d",
                        "." + BUILD_PATH_CLASS, testFile.getAbsolutePath());
        
        return result == 0 ? "" :  errorStream.toString();
    }

}
