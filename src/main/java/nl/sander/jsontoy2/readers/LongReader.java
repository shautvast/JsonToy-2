package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;
import nl.sander.jsontoy2.JsonObjectReader;

public class LongReader implements JsonObjectReader<Long> {
    @Override
    public Long read(IoReader ioReader) {
        return ioReader.readLong();
    }
}
