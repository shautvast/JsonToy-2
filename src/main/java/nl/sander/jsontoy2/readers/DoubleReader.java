package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;
import nl.sander.jsontoy2.JsonObjectReader;

import java.io.Reader;

public class DoubleReader implements JsonObjectReader<Double> {
    @Override
    public Double read(IoReader ioReader) {
        return ioReader.readDouble();
    }

}
