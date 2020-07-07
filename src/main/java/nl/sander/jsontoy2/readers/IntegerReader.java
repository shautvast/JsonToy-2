package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;
import nl.sander.jsontoy2.JsonObjectReader;

public class IntegerReader implements JsonObjectReader<Integer> {
    @Override
    public Integer read(IoReader ioReader) {
        return ioReader.readInteger();
    }
}
