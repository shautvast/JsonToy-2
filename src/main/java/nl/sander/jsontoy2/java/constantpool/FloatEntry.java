package nl.sander.jsontoy2.java.constantpool;

public class FloatEntry extends ConstantPoolEntry {
    private final float floatVal;

    public FloatEntry(float floatVal) {
        this.floatVal = floatVal;
    }

    @Override
    public String toString() {
        return "FloatEntry{" +
                "floatVal=" + floatVal +
                '}';
    }
}
