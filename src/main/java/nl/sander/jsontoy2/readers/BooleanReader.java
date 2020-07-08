package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;
import nl.sander.jsontoy2.JsonValueReader;

public class BooleanReader implements JsonValueReader<Boolean> {
    @Override
    public Boolean read(IoReader ioReader) {
        return ioReader.readBoolean();
    }
}
