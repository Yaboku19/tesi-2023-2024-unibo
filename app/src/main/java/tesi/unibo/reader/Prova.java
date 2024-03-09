package tesi.unibo.reader;

import org.json.JSONArray;
import org.json.JSONObject;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;

public class Prova {
    public static void main(String[] args) {
        try {
            // Ottieni il percorso del file JSON all'interno della cartella resources
            InputStream inputStream = Prova.class.getClassLoader().getResourceAsStream("tests.json");

            // Leggi il contenuto del file JSON
            String jsonContent = new String(inputStream.readAllBytes());


            // Converti la stringa JSON in un oggetto JSONObject
            JSONObject json = new JSONObject(jsonContent);

            // Estrai l'array di test dal JSON
            JSONArray tests = json.getJSONArray("tests");

            // Itera attraverso ogni test
            for (int i = 0; i < tests.length(); i++) {
                JSONObject test = tests.getJSONObject(i);
                String packageName = test.getString("package");
                String className = test.getString("className");
                JSONArray imports = test.getJSONArray("imports");
                String method = test.getString("method");

                // Genera un file Java temporaneo per il test
                String testFileContent = generateTestFileContent(imports, className, method, packageName);
                File testFile = new File("app/src/test/java/"+ packageName.replace(".", "/") + "/" +className + ".java");
                Files.writeString(testFile.toPath(), testFileContent);
                
                String junitJupiterClass = "org.junit.jupiter.api.Assertions";
                String junitJupiterJarPath = findJarPath(junitJupiterClass);
                System.out.println("JUnit Jupiter JAR Path: " + junitJupiterJarPath);

                // Compila il file Java
                JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                compiler.run(null, null, null, testFile.getAbsolutePath());
            }
        }catch( IOException e) {
            e.printStackTrace();
        }

                // Genera un file Java temporaneo per il test
                /*String testFileContent = generateTestFileContent(imports, className, method);
                File testFile = new File(className + ".java");
                Files.writeString(testFile.toPath(), testFileContent);

                // Compila il file Java
                JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                compiler.run(null, null, null, testFile.getAbsolutePath());

                // Carica e esegui il test
                Class<?> loadedClass = Class.forName(packageName + "." + className);
                Object testInstance = loadedClass.getDeclaredConstructor().newInstance();
                loadedClass.getMethod("dynamicTest").invoke(testInstance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private static String generateTestFileContent(JSONArray imports, String className, String method, String pacakge) {
        StringBuilder content = new StringBuilder();
        content.append("package " + pacakge + ";").append("\n");
        for (int i = 0; i < imports.length(); i++) {
            content.append(imports.getString(i)).append("\n");
        }
        content.append("\n");
        content.append("public class ").append(className).append(" {\n");
        content.append("    ").append(method).append("\n");
        content.append("}\n");
        return content.toString();
    }

    private static String findJarPath(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            ProtectionDomain protectionDomain = clazz.getProtectionDomain();
            if (protectionDomain != null) {
                CodeSource codeSource = protectionDomain.getCodeSource();
                if (codeSource != null) {
                    URL location = codeSource.getLocation();
                    if (location != null) {
                        String path = location.getPath();
                        if (path.endsWith(".jar")) {
                            return path;
                        } else if (path.endsWith("/")) {
                            return path.substring(0, path.length() - 1);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
