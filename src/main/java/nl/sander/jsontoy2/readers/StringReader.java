package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.JsonValueReader;
import nl.sander.jsontoy2.Parser;

public class StringReader extends JsonValueReader<String> {

    @Override
    public String read(Parser parser) {
        return parser.parseString();
    }
}
