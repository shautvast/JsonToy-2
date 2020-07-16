package nl.sander.jsontoy2.java.constantpool;

public class DoubleEntry extends ConstantPoolEntry {
    private final double doubleVal;

    public DoubleEntry(double doubleVal) {
        this.doubleVal = doubleVal;
    }

    @Override
    public String toString() {
        return "DoubleEntry{" +
                "doubleVal=" + doubleVal +
                '}';
    }
}
