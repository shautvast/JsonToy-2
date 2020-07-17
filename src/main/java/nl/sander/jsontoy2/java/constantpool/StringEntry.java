package nl.sander.jsontoy2.java.constantpool;

public class StringEntry extends ConstantPoolEntry {
    private final int utf8Index;

    public StringEntry(int utf8Index) {
        this.utf8Index = utf8Index;
    }

    @Override
    public String toString() {
        return "StringEntry{" +
                "utf8Index=" + utf8Index +
                '}';
    }
}
