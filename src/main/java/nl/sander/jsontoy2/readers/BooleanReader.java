package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;

public class BooleanReader implements nl.sander.jsontoy2.JsonObjectReader<Boolean> {
    @Override
    public Boolean read(IoReader ioReader) {
        return ioReader.readBoolean();
    }
}
