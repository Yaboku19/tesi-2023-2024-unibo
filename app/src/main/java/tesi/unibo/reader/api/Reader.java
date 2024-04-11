package tesi.unibo.reader.api;

import java.io.IOException;

public interface Reader {

    String readFromFIle(String url) throws IOException;

    String getName(String data);
}
