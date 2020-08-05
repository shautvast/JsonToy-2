package nl.sander.jsontoy2;

import nl.sander.jsontoy2.beans.BooleanBean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BeanWithBoolean {
    @Test
    public void testTrue() {
        assertEquals(new BooleanBean(true, true), JsonReader.read(BooleanBean.class, "{\"value\": true, \"value2\": true}"));
    }
}
