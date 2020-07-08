package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;
import nl.sander.jsontoy2.JsonValueReader;

public class ShortReader implements JsonValueReader<Short> {

    @Override
    public Short read(IoReader ioReader) {
        return ioReader.readShort();
    }
}
