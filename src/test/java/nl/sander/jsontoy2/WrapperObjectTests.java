package nl.sander.jsontoy2;

import nl.sander.jsontoy2.beans.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WrapperObjectTests {

    @Test
    public void testNested() {
        SimpleBean bean = JsonReader.read(SimpleBean.class, "{\"data1\": \"value1\",\"data2\": \"value2\"}");
        assertEquals(SimpleBean.class, bean.getClass());

        assertEquals("value1", bean.getData1());
        assertEquals("value2", bean.getData2());
    }

    @Test
    public void testBoolean() {
        BooleanBean trueBean = JsonReader.read(BooleanBean.class, "{\"value\": true}");
        assertTrue(trueBean.isValue());
        // second call to read, must not recreate class definition, (it would not compile)
        // so this test implicitly tests caching function too
        BooleanBean falseBean = JsonReader.read(BooleanBean.class, "{\"value2\": false}");
        assertFalse(falseBean.isValue());
    }

    @Test
    public void testString() {
        StringBean stringBean = JsonReader.read(StringBean.class, "{\"value\": \"haha\"}");
        assertEquals("haha", stringBean.getValue());
    }

    @Test
    public void testInteger() {
        IntegerBean integerBean = JsonReader.read(IntegerBean.class, "{\"value\": 1,\"value2\": 2}");
        assertEquals(1, integerBean.getValue());
        assertEquals(Integer.valueOf(2), integerBean.getValue2());
    }

    @Test
    public void testLong() {
        LongBean longBean = JsonReader.read(LongBean.class, "{\"value\": 100000000000,\"value2\": 100000000000}");
        assertEquals(100000000000L, longBean.getValue());
        assertEquals(Long.valueOf(100000000000L), longBean.getValue2());
    }

    @Test
    public void testFloat() {
        FloatBean floatBean = JsonReader.read(FloatBean.class, "{\"value\": 1.0,\"value2\": 100000000000}");
        assertEquals(1.0F, floatBean.getValue(), 0.1F);
        assertEquals(100000000000.0F, floatBean.getValue2(), 0.1F);
    }

    @Test
    public void testDouble() {
        DoubleBean doubleBean = JsonReader.read(DoubleBean.class, "{\"value\": 1.0,\"value2\": 100000000000}");
        assertEquals(1.0D, doubleBean.getValue(), 0.1D);
        assertEquals(100000000000.0D, doubleBean.getValue2(), 0.1D);
    }

    @Test
    public void testShort() {
        ShortBean shortBean = JsonReader.read(ShortBean.class, "{\"value\": 1,\"value2\": -11}");
        assertEquals(1, shortBean.getValue());
        assertEquals((short) -11, shortBean.getValue2().shortValue());
    }

    @Test
    public void testByte() {
        ByteBean byteBean = JsonReader.read(ByteBean.class, "{\"value\": 1,\"value2\": -11}");
        assertEquals(1, byteBean.getValue());
        assertEquals((byte) -11, byteBean.getValue2().shortValue());
    }

    @Test
    public void testStringList() {
        StringListBean listBean = JsonReader.read(StringListBean.class, "{\"value\": [\"a\",\"b\"]}");
        assertEquals(Arrays.asList("a", "b"), listBean.getValue());
    }

    @Test
    public void testStringSet() {
        StringSetBean listBean = JsonReader.read(StringSetBean.class, "{\"value\": [\"a\",\"b\"]}");
        assertEquals(new HashSet<>(Arrays.asList("a", "b")), listBean.getValue());
    }

    @Test
    public void testIntegerList() {
        IntegerListBean listBean = JsonReader.read(IntegerListBean.class, "{\"value\": [1,22]}");
        assertEquals(Arrays.asList(1, 22), listBean.getValue());
    }

    @Test
    public void testCharacterList() {
        CharacterListBean listBean = JsonReader.read(CharacterListBean.class, "{\"value\": [\"a\", \"[\", \"^\"]}");
        assertEquals(Arrays.asList('a', '[', '^'), listBean.getValue());
    }

    @Test
    public void testShortList() {
        ShortListBean listBean = JsonReader.read(ShortListBean.class, "{\"value\": [-1,0,1]}");
        assertEquals(Arrays.asList((short) -1, (short) 0, (short) 1), listBean.getValue());
    }

    @Test
    public void testBooleanList() {
        BooleanListBean listBean = JsonReader.read(BooleanListBean.class, "{\"value\": [true,false]}");
        assertEquals(Arrays.asList(true, false), listBean.getValue());
    }

    //    @Test
//    public void testFloatList() {
//        FloatListBean listBean = JsonReader.read(FloatListBean.class, "{\"value\": [-100.156,78.0]}");
//        assertEquals(Arrays.asList(-100.156F, 78.0F), listBean.getValue());
//    }
//
//    @Test
//    public void testByteList() {
//        ByteListBean listBean = JsonReader.read(ByteListBean.class, "{\"value\": [-100,78]}");
//        assertEquals(Arrays.asList((byte) -100, (byte) 78), listBean.getValue());
//    }
//
    @Test
    public void testDoubleList() {
        DoubleListBean listBean = JsonReader.read(DoubleListBean.class, "{\"value\": [-100.156,78.0]}");
        assertEquals(Arrays.asList(-100.156D, 78.0D), listBean.getValue());
    }

    @Test
    public void testNestedBean() {
        NestedBean nestedBean = JsonReader.read(NestedBean.class, "{\"value\": {\"value\": \"nested\"}}");
        assertEquals(new NestedBean(new InnerBean("nested")), nestedBean);
    }

    @Test
    public void testStringMap() {
        StringMapBean actual = JsonReader.read(StringMapBean.class, "{\"map\":  {\"a:\": \"b\", \"c:\" : \"d\" }}");
        StringMapBean expected = new StringMapBean("a:", "b", "c:", "d");
        assertEquals(expected, actual);
    }

    @Test
    public void testLinkedList() {
        LinkedListBean actual = JsonReader.read(LinkedListBean.class, "{\"list\": [\"a\"]}");
        LinkedList<String> actualList = actual.getList();

        assertEquals(List.of("a"), actualList);
    }

    @Test
    public void testArray() {
        ArrayBean actual = JsonReader.read(ArrayBean.class, "{\"array\": [\"a\"]}");

        assertEquals(1, actual.getArray().length);
        assertEquals("a", actual.getArray()[0]);
    }
}
