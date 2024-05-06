package tesi.unibo.reader.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;
import org.yaml.snakeyaml.Yaml;
import tesi.unibo.reader.api.Reader;
import java.util.Map;

public class ReaderFromYml extends Reader {
    public ReaderFromYml(final String pacakageTest, final String testName) {
        super(pacakageTest, testName);
    }

    @Override
    public String readFromFile(final String url) throws Exception {
        final InputStream inputStream = new FileInputStream(new File(ClassLoader.getSystemResource(url).toURI()));
        return generateTestFileContent(new Yaml().load(inputStream));
    }

    private String generateTestFileContent(final Map<String, String> data) {
        setClassName(data.get("class"));
        setSupportClassName(data.get("support") == null ? "" : data.get("support"));
        setImplementClassName(data.get("implement") == null ? "" : data.get("implement"));
        final StringBuilder content = new StringBuilder();
        content.append("package " + getPackageTest() + ";").append("\n");
        content.append(data.get("imports").replaceAll("(?m)^\\s+", "")).append("\n");
        content.append("\n");
        content.append("public class ").append(getTestName()).append(" {\n");
        int count = 1;
        final String code = data.get("code").replaceAll("(?m)^\\s+", "");
        for (int i = 0; i < code.length(); i++) {
            content.append(code.charAt(i));
            if (code.charAt(i) == '\n') {
                content.append(addTab(count));
            } else if (code.charAt(i) == '{') {
                count++;
            } else if (code.charAt(i) == '}') {
                count--;
                content.deleteCharAt(content.length() - 2);
            }
        }
        content.append("\n}");
        return content.toString();
    }
}
