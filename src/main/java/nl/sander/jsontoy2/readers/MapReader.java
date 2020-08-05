package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.JsonValueReader;
import nl.sander.jsontoy2.Parser;

import java.util.Map;

@SuppressWarnings("rawtypes")
public class MapReader extends JsonValueReader<Map> {
    @Override
    public Map read(Parser parser) {
        return parser.parseObject();
    }
}
