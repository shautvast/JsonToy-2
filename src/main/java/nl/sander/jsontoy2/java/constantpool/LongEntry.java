package nl.sander.jsontoy2.java.constantpool;

public class LongEntry extends ConstantPoolEntry {

    private final long longVal;

    public LongEntry(long longVal) {
        this.longVal = longVal;
    }

    @Override
    public String toString() {
        return "LongEntry{" +
                "longVal=" + longVal +
                '}';
    }
}
