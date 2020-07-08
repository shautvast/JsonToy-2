package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;
import nl.sander.jsontoy2.JsonValueReader;

public class FloatReader implements JsonValueReader<Float> {
    @Override
    public Float read(IoReader ioReader) {
        return ioReader.readFloat();
    }

}
