package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;
import nl.sander.jsontoy2.JsonValueReader;

import java.util.Map;

public class MapReader implements JsonValueReader<Map> {
    @Override
    public Map read(IoReader ioReader) {
        return ioReader.readMap();
    }
}
