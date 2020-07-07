package nl.sander.jsontoy2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Bytes {
    @Test
    public void testSimpleByte() {
        assertEquals((byte) 0, JsonReader.read(Byte.class, "0"));
    }

    @Test
    public void testSimplePrimitiveLong() {
        assertEquals((byte) 1, JsonReader.read(Byte.class, "1"));
    }

    @Test
    public void testSimpleNegativeLong() {
        assertEquals((byte) -20, JsonReader.read(Byte.class, "-20"));
    }

    @Test
    public void testSimpleNegativePrimitiveLong() {
        assertEquals(Byte.MIN_VALUE, JsonReader.read(Byte.class, "-128"));
    }
}
