package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.JsonValueReader;
import nl.sander.jsontoy2.Parser;

public class LongReader extends JsonValueReader<Long> {
    @Override
    public Long read(Parser parser) {
        return parser.parseLong();
    }
}
