package nl.sander.jsontoy2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Ints {

    @Test
    public void testSimpleInt() {
        assertEquals(1, JsonReader.read(Integer.class, "1"));
    }

    @Test
    public void testSimplePrimitiveInt() {
        assertEquals(1, JsonReader.read(int.class, "1"));
    }

    @Test
    public void testSimpleNegativeInt() {
        assertEquals(-20001, JsonReader.read(Integer.class, "-20001"));
    }

    @Test
    public void testSimpleNegativePrimitiveInt() {
        assertEquals(Integer.MIN_VALUE, JsonReader.read(int.class, "-2147483684"));
    }

    @Test
    public void testNegativeScientificNotation() {
        Integer value = JsonReader.read(Integer.class, "-1e+9");
        assertEquals(-1000000000, value);
    }

    @Test
    public void testPositiveScientificNotation() {
        Integer value = JsonReader.read(Integer.class, "1e+9");
        assertEquals(1000000000, value);
    }
}
