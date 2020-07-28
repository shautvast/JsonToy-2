package nl.sander.jsontoy2.java;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassReaderTest {

    //part of the test
    public int field;

    public String method(String value, int[] value2, int value3) {
        return value;
    }
    //

    @Test
    public void testReadClass() {
        ClassObject object = new ClassReader().parse(ClassReaderTest.class);
        assertEquals(Set.of(new Field("field", "I")), object.getFields());
        assertEquals(new TreeSet<>(Set.of(
                new Method("method", List.of("Ljava/lang/String", "[I", "I"), "Ljava/lang/String"),
                new Method("testReadClass", null, "V"),
                new Method("<init>", null, "V"))
        ), new TreeSet<>(object.getMethods()));
    }
}
