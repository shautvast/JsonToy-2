package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;
import nl.sander.jsontoy2.JsonObjectReader;

public class ShortReader implements JsonObjectReader<Short> {

    @Override
    public Short read(IoReader ioReader) {
        return ioReader.readShort();
    }
}
