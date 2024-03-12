package tesi.unibo.generator.impl;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.json.JSONArray;
import org.json.JSONObject;

import tesi.unibo.generator.Prova;
import tesi.unibo.generator.api.Generator;

public class GeneratorJson implements Generator {
    private static final String TEXT_PATH = "app/src/test/java/";
    private static final String EXTENSION = ".java";
    private static final String BUILD_PATH = "/app/build/classes/test/";
    @Override
    public Class<?> generateTest(final String url) {
        try {
            final InputStream inputStream = Prova.class.getClassLoader().getResourceAsStream(url);
            final String jsonContent = new String(inputStream.readAllBytes());
            final JSONObject json = new JSONObject(jsonContent);

            final String packageName = json.getString("package");
            final String className = json.getString("className");
            final String testFileContent = generateTestFileContent(json.getJSONArray("imports"), className,
                                                                    json.getJSONArray("tests"), packageName);
            final File testFile = new File(TEXT_PATH + packageName.replace(".", "/") 
                                            + "/" + className + EXTENSION);
            Files.writeString(testFile.toPath(), testFileContent);

            final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            compiler.run(null, null, null, "-d",
                         "." + BUILD_PATH, testFile.getAbsolutePath());

            try {
                String testJavaPath = System.getProperty("user.dir") + BUILD_PATH;
                URL testUrl = new File(testJavaPath).toURI().toURL();
                URLClassLoader testClassLoader = URLClassLoader.newInstance(new URL[]{testUrl});
                Class<?> generatedTestClass = testClassLoader.loadClass(packageName + "." + className);
                return generatedTestClass;
            } catch (ClassNotFoundException | MalformedURLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateTestFileContent(JSONArray imports, String className, JSONArray method, String pacakge) {
        StringBuilder content = new StringBuilder();
        content.append("package " + pacakge + ";").append("\n");
        for (int i = 0; i < imports.length(); i++) {
            content.append(imports.getString(i)).append("\n");
        }
        content.append("\n");
        content.append("public class ").append(className).append(" {\n");
        for (int i = 0; i < method.length(); i++) {
            content.append(method.getString(i)).append("\n");
        }
        content.append("}\n");
        return content.toString();
    }
    
}
