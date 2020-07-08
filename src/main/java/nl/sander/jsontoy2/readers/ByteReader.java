package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;
import nl.sander.jsontoy2.JsonValueReader;

public class ByteReader implements JsonValueReader<Byte> {
    @Override
    public Byte read(IoReader ioReader) {
        return ioReader.readByte();
    }
}
