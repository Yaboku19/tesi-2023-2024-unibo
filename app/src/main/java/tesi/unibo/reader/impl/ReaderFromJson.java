package tesi.unibo.reader.impl;

import java.io.IOException;
import tesi.unibo.reader.api.Reader;
import org.json.JSONObject;

public class ReaderFromJson implements Reader{
    private final String testName;
    private final String packageTest;
    private String className = "";

    public ReaderFromJson(final String pacakageTest, final String testName) {
        this.packageTest = pacakageTest;
        this.testName = testName;
    }
    @Override
    public String readFromFIle(String url) throws IOException {
        return generateTestFileContent(new String(
            ReaderFromJson.class.getClassLoader().getResourceAsStream(url).readAllBytes()
        ));
    }

    private String generateTestFileContent(final String data) {
        final JSONObject json = new JSONObject(data);
        className = json.getString("class");
        final StringBuilder content = new StringBuilder();
        content.append("package " + packageTest + ";").append("\n");
        for (int i = 0; i < json.getJSONArray("imports").length(); i++) {
            content.append(json.getJSONArray("imports").getString(i)).append("\n");
        }
        content.append("\n");
        content.append("public class ").append(testName).append(" {\n");
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
    public String getClassName() {
        return this.className;
    }
    
}
