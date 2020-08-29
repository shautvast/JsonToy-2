package nl.sander.jsontoy2.testobjects;

import nl.sander.jsontoy2.JsonReader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * test object
 */
public class DoubleListBean {
    private List<Double> value;

    public List<Double> getValue() {
        return value;
    }

    public void setValue(List<Double> value) {
        this.value = value;
    }

    @Test
    public void testTrue() {
        assertEquals(true, JsonReader.read(Boolean.class, "true"));
    }
}
