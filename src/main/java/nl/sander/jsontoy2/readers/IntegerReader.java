package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.JsonValueReader;
import nl.sander.jsontoy2.Parser;

public class IntegerReader implements JsonValueReader<Integer> {
    @Override
    public Integer read(Parser parser) {
        return parser.parseInteger();
    }
}
