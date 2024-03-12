package tesi.unibo.generator;

import org.json.JSONArray;
import org.json.JSONObject;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

import org.junit.jupiter.api.DynamicTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

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

                // Compila il file Java
                JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                compiler.run(null, null, null, "-d", "./app/build/classes/test/", testFile.getAbsolutePath());

                Result result = JUnitCore.runClasses(DynamicTest.class); // Sostituisci "YourTestClass" con il nome della tua classe di test

                // Gestisci il risultato dei test
                if (result.wasSuccessful()) {
                    System.out.println("Tutti i test sono passati!");
                } else {
                    System.out.println("Alcuni test sono falliti:");
                    for (Failure failure : result.getFailures()) {
                        System.out.println(failure.toString());
                    }
                }
                
            }
        }catch( Exception e) {
            e.printStackTrace();
        }
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
}
