package nl.sander.jsontoy2.java.constantpool;

public class ClassEntry extends ConstantPoolEntry {
    private final int nameIndex;

    public ClassEntry(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    @Override
    public String toString() {
        return "ClassEntry{" +
                "nameIndex=" + nameIndex +
                '}';
    }
}
