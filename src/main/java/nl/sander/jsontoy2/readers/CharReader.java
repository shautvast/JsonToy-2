package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;
import nl.sander.jsontoy2.JsonObjectReader;

public class CharReader implements JsonObjectReader<Character> {
    @Override
    public Character read(IoReader ioReader) {
        return ioReader.readCharacter();
    }
}
