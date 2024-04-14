package tesi.unibo.generator.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import org.json.JSONObject;
import tesi.unibo.generator.api.Generator;

public class GeneratorJson implements Generator {
    private static final String TEXT_PATH = "app/src/test/java/";
    private static final String CLASS_PATH = "app/src/main/java/";
    private static final String EXTENSION = ".java";
    private static final String BUILD_PATH_TEST = "/app/build/classes/java/test";
    private static final String BUILD_PATH_CLASS = "/app/build/classes/java/main";
    private final String packageTest;
    private final String packageClass;
    private String className = "";
    private static final String TEST_NAME = "DynamicTest";

    public GeneratorJson(final String packageTest, final String packageClass) {
        this.packageTest = packageTest;
        this.packageClass = packageClass;
    }
    
    @Override
    public Class<?> generateTest(final String testFileContent) throws IOException, ClassNotFoundException {
        final File testFile = Generator.generateFile(TEXT_PATH, packageTest, TEST_NAME, EXTENSION, testFileContent);

        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, "-d",
                        "." + BUILD_PATH_TEST, testFile.getAbsolutePath());

        final URL testUrl = new File(System.getProperty("user.dir") + BUILD_PATH_TEST).toURI().toURL();
        final URLClassLoader testClassLoader = URLClassLoader.newInstance(new URL[]{testUrl});
        return testClassLoader.loadClass(packageTest + "." + TEST_NAME);
    }

    public String generateTestFileContent(final String data) {
        final JSONObject json = new JSONObject(data);
        className = json.getString("class");
        final StringBuilder content = new StringBuilder();
        content.append("package " + packageTest + ";").append("\n");
        for (int i = 0; i < json.getJSONArray("imports").length(); i++) {
            content.append(json.getJSONArray("imports").getString(i)).append("\n");
        }
        content.append("\n");
        content.append("public class ").append(TEST_NAME).append(" {\n");
        int count = 1;
        for (int i = 0; i < json.getJSONArray("tests").length(); i++) {
            if (json.getJSONArray("tests").getString(i).endsWith("}")) {
                count--;
            }
            content.append(addTab(count));
            if (json.getJSONArray("tests").getString(i).endsWith("{")) {
                count++;
            }
            content.append(json.getJSONArray("tests").getString(i)).append("\n");
        }
        content.append("}\n");
        return content.toString();
    }

    private String addTab(final int count) {
        final StringBuilder toReturn = new StringBuilder();
        for (int j = 0; j < count; j++) {
            toReturn.append("\t");
        }
        return toReturn.toString();
    }

    @Override
    public int generateClass(final String data) throws IOException {
        Pattern pattern = Pattern.compile("```java(.*?)```", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(data);
        String codeJava = "";
        while (matcher.find()) {
            codeJava += matcher.group(1);
        }
        if (codeJava == "") {
            codeJava = data;
        }
        final File testFile = Generator.generateFile(CLASS_PATH, packageClass, className, EXTENSION, codeJava);
        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        final int result = compiler.run(null, null, null, "-d",
                        "." + BUILD_PATH_CLASS, testFile.getAbsolutePath());
        return result;
    }
}
