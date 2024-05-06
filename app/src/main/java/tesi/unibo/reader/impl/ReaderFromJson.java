package tesi.unibo.reader.impl;

import java.io.IOException;
import tesi.unibo.reader.api.Reader;
import org.json.JSONObject;

public class ReaderFromJson extends Reader{

    public ReaderFromJson(final String pacakageTest, final String testName) {
        super(pacakageTest, testName);
    }

    @Override
    public String readFromFile(String url) throws IOException {
        return generateTestFileContent(new String(
            ReaderFromJson.class.getClassLoader().getResourceAsStream(url).readAllBytes()
        ));
    }

    private String generateTestFileContent(final String data) {
        final JSONObject json = new JSONObject(data);
        setClassName(json.getString("class"));
        setSupportClassName(json.optString("support", ""));
        setImplementClassName(json.optString("implement", ""));
        final StringBuilder content = new StringBuilder();
        content.append("package " + getPackageTest() + ";").append("\n");
        for (int i = 0; i < json.getJSONArray("imports").length(); i++) {
            content.append(json.getJSONArray("imports").getString(i)).append("\n");
        }
        content.append("\n");
        content.append("public class ").append(getTestName()).append(" {\n");
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
    
}
