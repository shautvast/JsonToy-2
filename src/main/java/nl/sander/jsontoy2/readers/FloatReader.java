package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.JsonValueReader;
import nl.sander.jsontoy2.Parser;

public class FloatReader extends JsonValueReader<Float> {
    @Override
    public Float read(Parser parser) {
        return parser.parseFloat();
    }

}
