package nl.sander.jsontoy2;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("unchecked")
public class Lists {

    @Test
    public void emptyList() {
        List<String> list = JsonReader.read(List.class, "[]");
        assertEquals(List.of(), list);
    }

    @Test
    public void singleStringList() {
        List<String> list = JsonReader.read(List.class, "[\"hello jason\"]");
        assertEquals(List.of("hello jason"), list);
    }

    @Test
    public void multipleStringList() {
        List<String> list = JsonReader.read(List.class, "[\"hello\" , \"jason\"]");
        List<String> expected = List.of("hello", "jason");
        assertEquals(expected, list);
    }

    @Test
    public void multipleStrings_noComma_error() {
        List<String> list = JsonReader.read(List.class, " [\"hello\" \"jason\"]");
        List<String> expected = List.of("hello");
        assertEquals(expected, list);
    }

    @Test
    public void singleLong() {
        List<Long> list = JsonReader.read(List.class, " [ 1 ]");
        List<Long> expected = List.of(1L);
        assertEquals(expected, list);
    }

    @Test
    public void multipleLongs() {
        List<Long> list = JsonReader.read(List.class, "[1,2]");
        List<Long> expected = List.of(1L, 2L);
        assertEquals(expected, list);
    }

    @Test
    public void longDoubleBooleanString() {
        List<Integer> list = JsonReader.read(List.class, "[1, 2.5,false,    \"hello jason\"]");
        List<?> expected = List.of(1L, 2.5, false, "hello jason");
        assertEquals(expected, list);
    }

    @Test
    public void nestedList() {
        List<Integer> list = JsonReader.read(List.class, "[[],[]]");
        List<?> expected = List.of(List.of(), List.of());
        assertEquals(expected, list);
    }

    @Test
    public void mapInList() {
        List<Object> list = JsonReader.read(List.class, "[[],{\"list\":[]]}]");
        List<?> expected = List.of(List.of(), Map.of("list", List.of()));
        assertEquals(expected, list);
    }

    @Test
    public void nullInList() {
        List<Object> list = JsonReader.read(List.class, "[null]");
        List<?> expected = Collections.singletonList(null);
        assertEquals(expected, list);
    }
}