package nl.sander.jsontoy2;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("unchecked")
public class Maps {

    @Test
    public void emptyMap() {
        Map<Object, Object> map = JsonReader.read(Map.class, "{}");
        assertEquals(new HashMap<>(), map);
    }

    @Test
    public void singleStringKeyValueMap() {
        Map<String, String> map = JsonReader.read(Map.class, "{\"message\": \"hello jason\"}");
        assertEquals(Collections.singletonMap("message", "hello jason"), map);
    }

    @Test
    public void multipleValues() {
        Map<String, Object> map = JsonReader.read(Map.class, "{\"value1\" : \"jason\" ,\n \"value2\":1}");
        Map<String, Object> expected = new HashMap<>();
        expected.put("value1", "jason");
        expected.put("value2", 1);
        assertEquals(expected, map);
    }

    @Test
    public void multipleStrings_noColon_error() {
        assertThrows(JsonParseException.class, () -> JsonReader.read(Map.class, " {\"hello\" \"jason\"}"));
    }

    @Test
    public void singleInts() {
        Map<String, Integer> map = JsonReader.read(Map.class, " { \"1\":2 }");
        Map<String, Integer> expected = Collections.singletonMap("1", 2);
        assertEquals(expected, map);
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void nestedMap() {
        Map<String, Map> list = JsonReader.read(Map.class, "{\"map\": {\"map\":{}}}");
        Map<String, Map> expected = new HashMap<>();
        HashMap<String, Map> n1 = new HashMap<>();
        n1.put("map", new HashMap<>());
        expected.put("map", n1);

        assertEquals(expected, list);
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void listInMap() {
        Map<String, List> list = JsonReader.read(Map.class, " { \"list\" : [ 1 ] } ");
        Map<String, List> expected = new HashMap<>();
        expected.put("list", List.of(1));

        assertEquals(expected, list);
    }
}