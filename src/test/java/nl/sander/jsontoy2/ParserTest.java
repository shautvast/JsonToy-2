package nl.sander.jsontoy2;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    @Test
    public void testFloat() {
        assertEquals(31.415927F, new Parser(getInputStream("31.415927")).parseFloat());
    }

    @Test
    public void testDouble() {
        assertEquals(3.1415927D, new Parser(getInputStream("3.1415927")).parseDouble());
    }

    @Test
    public void testInt() {
        assertEquals(31415927, new Parser(getInputStream("31415927")).parseInteger());
    }

    @Test
    public void testShort() {
        assertEquals((short) 31415, new Parser(getInputStream("31415")).parseShort());
    }

    @Test
    public void testByte() {
        assertEquals((byte) 31, new Parser(getInputStream("31")).parseByte());
    }

    @Test
    public void testBooleanTrue() {
        assertEquals(true, new Parser(getInputStream("true")).parseBoolean());
    }

    @Test
    public void testBooleanFalse() {
        assertEquals(false, new Parser(getInputStream("false")).parseBoolean());
    }

    @Test
    public void testBooleanInvalid() {
        assertThrows(JsonParseException.class, () -> new Parser(getInputStream("falsy")).parseBoolean());
    }

    @Test
    public void testArray() {
        assertEquals(List.of("3", "1", "4", "1"), new Parser(getInputStream("[\"3\",\"1\",\"4\",\"1\"]")).parseArray());
    }

    @Test
    public void testArrayWithType() {
        assertEquals(List.of(3D, 1D, 4D, 1D), new Parser(getInputStream("[3,1,4,1]")).parseArray(Double.class));
    }

    @Test
    public void testObject() {
        assertEquals(Map.of("pi", 3.1415927D), new Parser(getInputStream("{\"pi\":3.1415927}")).parseObject());
    }

    @Test
    public void testObjectWithType() {
        assertEquals(Map.of("pi", 3.1415927F), new Parser(getInputStream("{\"pi\":3.1415927}")).parseObject(PiBean.class));
    }

    @Test
    public void testChar() {
        assertEquals('3', new Parser(getInputStream("\"3\"")).parseCharacter());
    }

    private InputStream getInputStream(String jsonString) {
        return new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));
    }

    static class PiBean {
        private float pi;
    }
}
