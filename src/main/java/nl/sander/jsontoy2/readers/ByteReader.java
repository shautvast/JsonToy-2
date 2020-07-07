package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;
import nl.sander.jsontoy2.JsonObjectReader;

public class ByteReader implements JsonObjectReader<Byte> {
    @Override
    public Byte read(IoReader ioReader) {
        return ioReader.readByte();
    }
}
