package nl.sander.jsontoy2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Chars {

    @Test
    public void testSimpleChar() {
        assertEquals('a', JsonReader.read(Character.class, "\"a\""));
    }

    @Test
    public void testSimplePrimitiveChar() {
        assertEquals('A', JsonReader.read(char.class, "\"A\""));
    }

    @Test
    public void testTooLongChar() {
        assertEquals('A', JsonReader.read(char.class, "\"AB\""));
    }

}
