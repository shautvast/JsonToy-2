package nl.sander.jsontoy2;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringsWithJackson {

    ObjectMapper jackson = new ObjectMapper();

    @Test
    public void regular() throws JsonProcessingException {
        assertEquals("DADA", jackson.readValue("\"DADA\"", String.class));
    }

    @Test
    public void firstSurrogateButSecondMissing() throws JsonProcessingException {
        String value = jackson.readValue("\"\\uDADA\"", String.class);
    }


    @Test
    public void incompleteSurrogateAndEscapeValid() {
        assertThrows(JsonParseException.class, () -> jackson.readValue("\"\\uD800\n\"", String.class));
    }

    @Test
    public void firstValidSurrogateSecondInvalid() throws JsonProcessingException {
        String value = jackson.readValue("\"\\uD888\\u1334\"", String.class);
        assertTrue(true);
    }

    @Test
    public void escapedDoubleQuote() throws JsonProcessingException {
        String value = jackson.readValue("\"\\\"\"", String.class);
        assertEquals("\"", value);
    }

    @Test
    public void escapedSingleQuote() {
        assertThrows(JsonParseException.class, () -> jackson.readValue("\"\\'\"", String.class));
    }

}
