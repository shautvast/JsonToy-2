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

    @Test
    public void tab() {
        assertEquals('\t', JsonReader.read(char.class, "\"\\t\""));
    }

    @Test
    public void backspace() {
        assertEquals('\b', JsonReader.read(char.class, "\"\\b\""));
    }

    @Test
    public void formfeed() {
        assertEquals('\f', JsonReader.read(char.class, "\"\\f\""));
    }

    @Test
    public void backslash() {
        assertEquals('\\', JsonReader.read(char.class, "\"\\\\\""));
    }

    @Test
    public void slash() {
        assertEquals('/', JsonReader.read(char.class, "\"\\/\""));
    }

    @Test
    public void newline() {
        assertEquals('\n', JsonReader.read(char.class, "\"\\n\""));
    }

    @Test
    public void unicode() {
        assertEquals('\u0100', JsonReader.read(char.class, "\"\\u0100\""));
    }

    @Test
    public void unicodeascii() {
        assertEquals('A', JsonReader.read(char.class, "\"\\u0041\""));
    }

    @Test
    public void testunicode32() {
//        int codepoint = Character.codePointOf("\\uD834\\uDD1E");
        assertEquals("\uD834\uDD1E", JsonReader.read("\"\\uD834\\uDD1E\""));
    }
}
