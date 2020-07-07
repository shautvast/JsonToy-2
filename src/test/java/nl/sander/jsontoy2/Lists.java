package nl.sander.jsontoy2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("unchecked")
public class Lists {

    @Test
    public void emptyList() {
        List<String> list = JsonReader.read(List.class, "[]");
        assertEquals(new ArrayList<>(), list);
    }

    @Test
    public void singleStringList() {
        List<String> list = JsonReader.read(List.class, "[\"hello jason\"]");
        assertEquals(Collections.singletonList("hello jason"), list);
    }

    @Test
    public void multipleStringList() {
        List<String> list = JsonReader.read(List.class, "[\"hello\" , \"jason\"]");
        List<String> expected = Arrays.asList("hello", "jason");
        assertEquals(expected, list);
    }

    @Test
    public void multipleStrings_noComma_error() {
        List<String> list = JsonReader.read(List.class, "[\"hello\" \"jason\"]");
        List<String> expected = Collections.singletonList("hello");
        assertEquals(expected, list);
    }

    @Test
    public void singleInt() {
        List<Integer> list = JsonReader.read(List.class, "[1]");
        List<Integer> expected = Collections.singletonList(1);
        assertEquals(expected, list);
    }

    @Test
    public void multipleInts() {
        List<Integer> list = JsonReader.read(List.class, "[1,2]");
        List<Integer> expected = Arrays.asList(1,2);
        assertEquals(expected, list);
    }

    @Test
    public void intDoubleBooleanString() {
        List<Integer> list = JsonReader.read(List.class, "[1,2.5,false,\"hello jason\"]");
        List<?> expected = Arrays.asList(1,2.5,false,"hello jason");
        assertEquals(expected, list);
    }

    @Test
    public void nestedList() {
        List<Integer> list = JsonReader.read(List.class, "[[],[]]");
        List<?> expected = Arrays.asList(List.of(), List.of());
        assertEquals(expected, list);
    }
}