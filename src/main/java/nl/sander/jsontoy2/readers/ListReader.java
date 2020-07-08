package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;
import nl.sander.jsontoy2.JsonValueReader;

import java.util.List;

@SuppressWarnings("rawtypes")
public class ListReader implements JsonValueReader<List> {
    @Override
    public List<?> read(IoReader ioReader) {
        return ioReader.readList();
    }
}
