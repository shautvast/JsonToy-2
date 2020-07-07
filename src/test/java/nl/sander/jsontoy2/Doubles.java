package nl.sander.jsontoy2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Doubles {

    @Test
    public void testSmallDouble() {
        Double value = JsonReader.read(Double.class, "123.456e-289");
        assertEquals(123.456e-289D, value);
    }

    @Test
    public void testLargeDouble() {
        Double value = JsonReader.read(Double.class, "999999999999999999999999999999999999999999999999999999.4e99");
        assertEquals(999999999999999999999999999999999999999999999999999999.4e99D, value);
    }

    @Test
    public void testNegativeDouble() {
        Double value = JsonReader.read(Double.class, "-1.2e+9");
        assertEquals(-1.2e+9D, value);
    }

    @Test
    public void testNegativeDoubleOverflow() {
        Double value = JsonReader.read(Double.class, "-1.2e+100000");
        assertEquals(Double.NEGATIVE_INFINITY, value);
    }

    @Test
    public void testNegativeFloatUnderflow() {
        Double value = JsonReader.read(Double.class, "-1.2e-100000");
        assertEquals(-0D, value);
    }

    @Test
    public void testPositiveFloatUnderflow() {
        Double value = JsonReader.read(Double.class, "1.2e-100000");
        assertEquals(0D, value);
    }


}
