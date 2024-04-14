package tesi.unibo.reader.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.io.File;
import org.yaml.snakeyaml.Yaml;
import java.util.Map;

public class ReaderFromYml {
    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
        final URL setupPath = ClassLoader.getSystemResource("tests.yml");
        InputStream inputStream;
          inputStream = new FileInputStream(new File(setupPath.toURI()));
          Yaml yaml = new Yaml();
          Map<String, String> data = yaml.load(inputStream);
          System.out.println(data.get("imports"));
    }
}
