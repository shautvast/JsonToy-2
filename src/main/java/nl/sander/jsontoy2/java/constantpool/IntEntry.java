package nl.sander.jsontoy2.java.constantpool;

public class IntEntry extends ConstantPoolEntry {
    private final int intVal;

    public IntEntry(int integer) {
        this.intVal = integer;
    }

    @Override
    public String toString() {
        return "IntEntry{" +
                "intVal=" + intVal +
                '}';
    }
}
