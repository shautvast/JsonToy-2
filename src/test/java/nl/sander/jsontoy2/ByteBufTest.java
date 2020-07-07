package nl.sander.jsontoy2;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByteBufTest {


    @Test
    public void testAdd(){
        ByteBuf c=new ByteBuf(1);
        c.add("1".getBytes(StandardCharsets.UTF_8));
        assertEquals("1", c.toString());
    }

    @Test
    public void testExpansion(){
        ByteBuf c=new ByteBuf(1);
        c.add("1".getBytes());
        c.add("2".getBytes());
        assertEquals("12", c.toString());
    }

    @Test
    public void testClear(){
        ByteBuf c=new ByteBuf(1);
        c.add("1".getBytes());
        c.clear();
        c.add("2".getBytes());
        assertEquals("2", c.toString());
    }

    @Test
    public void testLength(){
        ByteBuf c=new ByteBuf(10);
        c.add("1".getBytes());
        c.clear();
        c.add("2".getBytes());
        assertEquals(1, c.length());
    }

    @Test
    public void testUtf(){
        ByteBuf c=new ByteBuf(1);
        c.add("東".getBytes(StandardCharsets.UTF_8));
        assertEquals("東", c.toString());
    }

    @Test
    public void testIllegalUtf(){
        ByteBuf c=new ByteBuf(1);
        c.add(new byte[]{0,0,-40,0});
        assertEquals("\u0000\u0000�\u0000", c.toString());
    }
}
