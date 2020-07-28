package nl.sander.jsontoy2.java;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassReaderTest {

    //part of the test
    public int field;

    @Test
    public void testReadClass() {
        ClassObject object = new ClassReader().parse(ClassReaderTest.class);
        assertEquals(Set.of(new Field("field", "I")), object.getFields());
        assertEquals(Set.of(new Method("<init>", "()V"), new Method("testReadClass", "()V")), object.getMethods());
    }
}
