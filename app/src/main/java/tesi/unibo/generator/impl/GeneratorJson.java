package tesi.unibo.generator.impl;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import org.json.JSONObject;
import tesi.unibo.generator.api.Generator;

public class GeneratorJson implements Generator {
    private static final String TEXT_PATH = "app/src/test/java/";
    private static final String EXTENSION = ".java";
    private static final String BUILD_PATH = "/app/build/classes/test/";
    private static final String PACKAGE = "tesi.unibo.dynamic.impl";
    private static final String CLASS_NAME = "DynamicTest";
    
    @Override
    public Class<?> generateTest(final String testFileContent) {
        try {
            final File testFile = new File(TEXT_PATH + PACKAGE.replace(".", "/") + "/" + CLASS_NAME + EXTENSION);
            Files.writeString(testFile.toPath(), testFileContent);

            final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            compiler.run(null, null, null, "-d",
                         "." + BUILD_PATH, testFile.getAbsolutePath());

            final URL testUrl = new File(System.getProperty("user.dir") + BUILD_PATH).toURI().toURL();
            final URLClassLoader testClassLoader = URLClassLoader.newInstance(new URL[]{testUrl});
            return testClassLoader.loadClass(PACKAGE + "." + CLASS_NAME);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generateTestFileContent(final String data) {
        final JSONObject json = new JSONObject(data);
        final StringBuilder content = new StringBuilder();
        content.append("package " + PACKAGE + ";").append("\n");
        content.append("import tesi.unibo.dynamic."+ json.getString("class")+ ";\n");
        for (int i = 0; i < json.getJSONArray("imports").length(); i++) {
            content.append(json.getJSONArray("imports").getString(i)).append("\n");
        }
        content.append("\n");
        content.append("public class ").append(CLASS_NAME).append(" {\n");
        content.append(getConstructor()).append("\n");
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

    private String getConstructor() {
        return "\tpublic "+ CLASS_NAME +"() {\n\t}\n";
    }
}
