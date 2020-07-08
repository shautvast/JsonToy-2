package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;
import nl.sander.jsontoy2.JsonValueReader;

public class StringReader implements JsonValueReader<String> {

    @Override
    public String read(IoReader ioReader) {
        return ioReader.readString();
    }
}
