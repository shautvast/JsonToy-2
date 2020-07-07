package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;
import nl.sander.jsontoy2.JsonObjectReader;

public class StringReader implements JsonObjectReader<String> {

    @Override
    public String read(IoReader ioReader) {
        return ioReader.readString();
    }
}
