package nl.sander.jsontoy2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Booleans {

    @Test
    public void testTrue() {
        assertEquals(true, JsonReader.read(Boolean.class, "true"));
    }

    @Test
    public void testIllegalTrue() {
        assertThrows(JsonParseException.class, () -> JsonReader.read(Boolean.class, "TRUE"));
    }

    @Test
    public void testIllegalFalse() {
        assertThrows(JsonParseException.class, () -> JsonReader.read(Boolean.class, "False"));
    }

    @Test
    public void testFalse() {
        assertEquals(false, JsonReader.read(Boolean.class, "false"));
    }

    @Test
    public void testPrimitiveFalse() {
        boolean value = JsonReader.read(boolean.class, "false");
        assertFalse(value);
    }
}
