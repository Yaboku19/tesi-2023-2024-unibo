package tesi.unibo.reader.impl;

import java.io.IOException;

import org.json.JSONObject;

import tesi.unibo.generator.impl.GeneratorJson;
import tesi.unibo.reader.api.Reader;

public class ReaderFromJson implements Reader{

    @Override
    public String readFromFIle(String url) throws IOException {
        return new String(
            GeneratorJson.class.getClassLoader().getResourceAsStream(url).readAllBytes()
        );
    }

    @Override
    public String getName(String data) {
        final JSONObject json = new JSONObject(data);
        return json.getString("class");
    }
    
}
