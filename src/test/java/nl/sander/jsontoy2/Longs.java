package nl.sander.jsontoy2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Longs {
    @Test
    public void testSimpleLong(){
        assertEquals(0,JsonReader.read(Long.class,"0"));
    }

    @Test
    public void testSimplePrimitiveLong(){
        assertEquals(1,JsonReader.read(long.class,"1"));
    }

    @Test
    public void testSimpleNegativeLong(){
        assertEquals(-20001,JsonReader.read(Long.class,"-20001"));
    }

    @Test
    public void testSimpleNegativePrimitiveLong(){
        assertEquals(Long.MIN_VALUE,JsonReader.read(long.class,"-9223372036854775808"));
    }
}
