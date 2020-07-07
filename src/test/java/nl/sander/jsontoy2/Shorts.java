package nl.sander.jsontoy2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Shorts {

    @Test
    public void testSimpleShort() {
        assertEquals((short) 1, JsonReader.read(Short.class, "1"));
    }

    @Test
    public void testSimplePrimitiveShort() {
        assertEquals((short) 1, JsonReader.read(short.class, "1"));
    }

    @Test
    public void testSimpleNegativeShort() {
        assertEquals((short) -100, JsonReader.read(Short.class, "-100"));
    }

    @Test
    public void testSimpleNegativePrimitiveShort() {
        assertEquals((short) -100, JsonReader.read(short.class, "-100"));
    }
}
