package nl.sander.jsontoy2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class Null {
    @Test
    public void readNull() {
        assertNull(JsonReader.read("null"));
    }

}
