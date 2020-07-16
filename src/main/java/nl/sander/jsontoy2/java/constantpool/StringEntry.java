package nl.sander.jsontoy2.java.constantpool;

public class StringEntry extends ConstantPoolEntry {
    private final short utf8Index;

    public StringEntry(short utf8Index) {
        this.utf8Index = utf8Index;
    }

    @Override
    public String toString() {
        return "StringEntry{" +
                "utf8Index=" + utf8Index +
                '}';
    }
}
