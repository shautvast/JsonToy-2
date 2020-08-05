package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.JsonValueReader;
import nl.sander.jsontoy2.Parser;

public class BooleanReader extends JsonValueReader<Boolean> {
    @Override
    public Boolean read(Parser parser) {
        return parser.parseBoolean();
    }
}
