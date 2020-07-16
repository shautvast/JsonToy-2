package nl.sander.jsontoy2.java.constantpool;

public class ClassEntry extends ConstantPoolEntry {
    private final short nameIndex;

    public ClassEntry(short nameIndex) {
        this.nameIndex = nameIndex;
    }

    @Override
    public String toString() {
        return "ClassEntry{" +
                "nameIndex=" + nameIndex +
                '}';
    }
}
