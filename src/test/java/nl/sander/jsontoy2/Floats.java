package nl.sander.jsontoy2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Floats {

    @Test
    public void testSmallFloat() {
        Float value = JsonReader.read(Float.class, "123.456e-28");
        assertEquals(123.456e-28F, value);
    }

    @Test
    public void testLargeFloat() {
        Float value = JsonReader.read(Float.class, "9999.4e29");
        assertEquals(9999.4e29F, value);
    }

    @Test
    public void testNegativeFloat() {
        Float value = JsonReader.read(Float.class, "-1.2e+9");
        assertEquals(-1.2e+9F, value);
    }

    @Test
    public void testNegativeFloatOverflow() {
        Float value = JsonReader.read(Float.class, "-1.2e+100000");
        assertEquals(Float.NEGATIVE_INFINITY, value);
    }

    @Test
    public void testNegativeFloatUnderflow() {
        Float value = JsonReader.read(Float.class, "-1.2e-100000");
        assertEquals(-0F, value);
    }

    @Test
    public void testPositiveFloatUnderflow() {
        Float value = JsonReader.read(Float.class, "1.2e-100000");
        assertEquals(0F, value);
    }


}
