package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;
import nl.sander.jsontoy2.JsonValueReader;

public class LongReader implements JsonValueReader<Long> {
    @Override
    public Long read(IoReader ioReader) {
        return ioReader.readLong();
    }
}
