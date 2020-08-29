package nl.sander.jsontoy2;

import nl.sander.jsontoy2.testobjects.BooleanBean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BeanWithBoolean {
    @Test
    public void testTrue() {
        // Arrange
        BooleanBean booleanBean = JsonReader.read(BooleanBean.class, "{\"value\": true, \"value2\": true}");

        // Assert
        assertEquals(new BooleanBean(true, Boolean.TRUE), booleanBean);
    }

    @Test
    public void testFalse() {
        // Arrange
        BooleanBean booleanBean = JsonReader.read(BooleanBean.class, "{\"value\": false, \"value2\": false}");

        // Assert
        assertEquals(new BooleanBean(false, Boolean.FALSE), booleanBean);
    }

    @Test
    public void testIllegalValues() {
        // Assert
        assertThrows(JsonParseException.class, () -> JsonReader.read(BooleanBean.class, "{\"value\": true, \"value2\": True}"));
    }
}
