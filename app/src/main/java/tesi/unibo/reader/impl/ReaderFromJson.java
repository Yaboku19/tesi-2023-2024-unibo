package tesi.unibo.reader.impl;

import java.io.IOException;

import tesi.unibo.generator.impl.GeneratorJson;
import tesi.unibo.reader.api.Reader;
import java.util.Map;
import java.util.HashMap;

public class ReaderFromJson implements Reader{

    @Override
    public Map<String, String> readFromFIle(String url) throws IOException {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("jsonCode", new String(
            GeneratorJson.class.getClassLoader().getResourceAsStream(url).readAllBytes()
        ));
        return dataMap;
    }
    
}
