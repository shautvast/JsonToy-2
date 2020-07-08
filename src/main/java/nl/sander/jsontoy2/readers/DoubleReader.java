package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;
import nl.sander.jsontoy2.JsonValueReader;

public class DoubleReader implements JsonValueReader<Double> {
    @Override
    public Double read(IoReader ioReader) {
        return ioReader.readDouble();
    }

}
