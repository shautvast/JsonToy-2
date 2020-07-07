package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;
import nl.sander.jsontoy2.JsonObjectReader;

public class FloatReader implements JsonObjectReader<Float> {
    @Override
    public Float read(IoReader ioReader) {
        return ioReader.readFloat();
    }

}
