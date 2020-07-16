package nl.sander.jsontoy2;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomObject {

    @Test
    public void testRandomJsonObject() {
        Map<?, ?> map = JsonReader.read(Map.class, getClass().getResourceAsStream("/random_object.json"));
        assertEquals(5, map.size());
    }
}
