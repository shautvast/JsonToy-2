package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.JsonValueReader;
import nl.sander.jsontoy2.Parser;

import java.util.List;

@SuppressWarnings("rawtypes")
public class ListReader implements JsonValueReader<List> {
    @Override
    public List<?> read(Parser parser) {
        return parser.parseArray();
    }
}
