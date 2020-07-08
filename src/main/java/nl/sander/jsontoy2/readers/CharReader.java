package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;
import nl.sander.jsontoy2.JsonValueReader;

public class CharReader implements JsonValueReader<Character> {
    @Override
    public Character read(IoReader ioReader) {
        return ioReader.readCharacter();
    }
}
