package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;
import nl.sander.jsontoy2.JsonValueReader;

public class IntegerReader implements JsonValueReader<Integer> {
    @Override
    public Integer read(IoReader ioReader) {
        return ioReader.readInteger();
    }
}
