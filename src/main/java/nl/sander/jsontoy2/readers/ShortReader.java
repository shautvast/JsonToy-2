package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.JsonValueReader;
import nl.sander.jsontoy2.Parser;

public class ShortReader implements JsonValueReader<Short> {

    @Override
    public Short read(Parser parser) {
        return parser.parseShort();
    }
}
