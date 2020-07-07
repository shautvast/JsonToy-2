package nl.sander.jsontoy2;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Strings {

    @Test
    public void regular() {
        assertEquals("DADA", JsonReader.read(String.class, "\"DADA\""));
    }

    @Test
    public void firstSurrogateButSecondMissing() throws NoSuchFieldException, IllegalAccessException {
        String value = JsonReader.read(String.class, "\"\\uDADA\"");

        assertEquals("\u0000\u0000��", value); // question mark
    }


    @Test
    public void incompleteSurrogateAndEscapeValid() {
        String value = JsonReader.read(String.class, "\"\\uD800\n\"");
        assertEquals("\u0000\u0000�\u0000\n", value);
    }

    @Test
    public void firstValidSurrogateSecondInvalid() throws CharacterCodingException {
        String value = JsonReader.read(String.class, "\"\\uD888\\u1334\"");

        assertEquals("\u0000\u0000؈\u0000\u0000\u00134",value);
    }

    @Test
    public void escapedDoubleQuote() {
        String value = JsonReader.read(String.class, "\"\\\"\"");
        assertEquals("\"", value);
    }

    @Test
    public void escapedSingleQuote() {
        assertThrows(JsonReadException.class, () -> JsonReader.read(String.class, "\"\\\'\""));
    }

}
