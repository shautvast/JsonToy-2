package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.JsonValueReader;
import nl.sander.jsontoy2.Parser;

public class ByteReader extends JsonValueReader<Byte> {
    @Override
    public Byte read(Parser parser) {
        return parser.parseByte();
    }
}
