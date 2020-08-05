package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.JsonValueReader;
import nl.sander.jsontoy2.Parser;

public class DoubleReader extends JsonValueReader<Double> {
    @Override
    public Double read(Parser parser) {
        return parser.parseDouble();
    }

}
