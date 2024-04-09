package tesi.unibo.generator.impl;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import tesi.unibo.generator.api.Generator;

public class GeneratorJson implements Generator {
    private static final String TEXT_PATH = "app/src/test/java/";
    private static final String EXTENSION = ".java";
    private static final String BUILD_PATH = "/app/build/classes/test/";
    private static final String PACKAGE = "tesi.unibo.dynamic.impl";
    private static final String CLASS_NAME = "DynamicTest";
    
    @Override
    public Class<?> generateTest(final String data) {
        try {
            final JSONObject json = new JSONObject(data);
            final String testFileContent = generateTestFileContent(json.getJSONArray("imports"), CLASS_NAME, json.getJSONArray("tests"), 
                                                                    PACKAGE, json.getString("class"));
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

    private String generateTestFileContent(final JSONArray imports, final String className, final JSONArray method, final String pacakge, final String classImport) {
        final StringBuilder content = new StringBuilder();
        content.append("package " + pacakge + ";").append("\n");
        content.append("import tesi.unibo.dynamic."+ classImport+ ";\n");
        for (int i = 0; i < imports.length(); i++) {
            content.append(imports.getString(i)).append("\n");
        }
        content.append("\n");
        content.append("public class ").append(className).append(" {\n");
        content.append(getConstructor()).append("\n");
        int count = 1;
        for (int i = 0; i < method.length(); i++) {
            if (method.getString(i).endsWith("}")) {
                count--;
            }
            content.append(addTab(count));
            if (method.getString(i).endsWith("{")) {
                count++;
            }
            content.append(method.getString(i)).append("\n");
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
