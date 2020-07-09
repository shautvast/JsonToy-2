package nl.sander.jsontoy2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Strings {

    @Test
    public void regular() {
        assertEquals("DADA", JsonReader.read(String.class, "\"DADA\""));
    }

    @Test
    public void firstSurrogateButSecondMissing() {
        assertThrows(JsonParseException.class, () -> JsonReader.read(String.class, "\"\\uDADA\""));
    }

    @Test
    public void incompleteSurrogateAndEscapeValid() {
        assertThrows(JsonParseException.class, () -> JsonReader.read(String.class, " \"\\uD800\n\""));
    }

    @Test
    public void firstValidSurrogateSecondInvalid() {
        String value = JsonReader.read(String.class, "\"\\uD888\\u1334\"");
        assertEquals("?", value);
    }

    @Test
    public void escapedDoubleQuote() {
        String value = JsonReader.read(String.class, " \"\\\"\"");
        assertEquals("\"", value);
    }

    @Test
    public void escapedSingleQuote() {
        assertThrows(JsonParseException.class, () -> JsonReader.read(String.class, "\"\\'\""));
    }

}
