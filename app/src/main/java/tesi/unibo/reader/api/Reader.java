package tesi.unibo.reader.api;

import java.io.IOException;
import java.util.Map;

public interface Reader {

    Map<String, String> readFromFIle(String url) throws IOException;
}
