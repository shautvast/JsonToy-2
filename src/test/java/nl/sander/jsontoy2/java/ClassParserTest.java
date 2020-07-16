package nl.sander.jsontoy2.java;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassParserTest {

    private int field;

    @Test
    public void testReadClass() {
        ClassObject<ClassParserTest> object = new ClassParser().parse(ClassParserTest.class);
        assertEquals(Set.of(new Field("field", "I")), object.getFields());
    }

    // if not included, field is not in the compiled code.
    public int getField() {
        return field;
    }
}
