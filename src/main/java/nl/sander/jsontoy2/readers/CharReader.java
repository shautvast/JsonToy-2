package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.JsonValueReader;
import nl.sander.jsontoy2.Parser;

public class CharReader implements JsonValueReader<Character> {
    @Override
    public Character read(Parser parser) {
        return parser.parseCharacter();
    }
}
